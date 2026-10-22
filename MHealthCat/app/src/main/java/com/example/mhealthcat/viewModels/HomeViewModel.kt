package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mhealthcat.data.points.Mood
import com.example.mhealthcat.data.points.PointsCalculator
import com.example.mhealthcat.data.points.ScoredEntry
import com.example.mhealthcat.data.repositories.MhealthDataRepositories
import com.example.mhealthcat.elementsAndClasses.DataType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class PointsState(
    val todayPoints: Int = 0,
    val totalPoints: Int = 0,
    val mood: Mood = Mood.NEUTRAL
)


class HomeViewModel : ViewModel() {

    val pointsState: StateFlow<PointsState> = combine(
        MhealthDataRepositories.sleepRepository.items,
        MhealthDataRepositories.sportRepository.items,
        MhealthDataRepositories.socialRepository.items,
        MhealthDataRepositories.wellbeingRepository.items
    ) { sleep, sport, social, wellbeing ->
        val entries =
            sleep.map { ScoredEntry(DataType.SLEEP, it.timestamp, PointsCalculator.sleepPoints(it)) } +
            sport.map { ScoredEntry(DataType.SPORT, it.timestamp, PointsCalculator.ENTRY_POINTS) } +
            social.map { ScoredEntry(DataType.SOCIAL, it.timestamp, PointsCalculator.ENTRY_POINTS) } +
            wellbeing.map { ScoredEntry(DataType.WELLBEING, it.timestamp, PointsCalculator.ENTRY_POINTS) }

        val today = PointsCalculator.todayPoints(entries)
        PointsState(
            todayPoints = today,
            totalPoints = PointsCalculator.totalPoints(entries),
            mood = PointsCalculator.mood(today)
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PointsState())
}
