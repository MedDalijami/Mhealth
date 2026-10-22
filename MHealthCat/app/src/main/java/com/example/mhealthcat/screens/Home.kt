package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.R
import com.example.mhealthcat.data.points.Mood
import com.example.mhealthcat.elementsAndClasses.BackgroundAnimation
import com.example.mhealthcat.viewModels.HomeViewModel
import com.example.mhealthcat.elementsAndClasses.PointsHeader

@Composable
fun Home() {
    val homeViewModel: HomeViewModel = viewModel()
    val pointsState by homeViewModel.pointsState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        PointsHeader (
            todayPoints = pointsState.todayPoints,
            totalPoints = pointsState.totalPoints,
            mood = pointsState.mood
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            key(pointsState.mood) {
                BackgroundAnimation(
                    videoResId = moodAnimation(pointsState.mood),
                    modifier = Modifier.size(500.dp)
                )
            }
        }
    }
}
private fun moodAnimation(mood: Mood): Int = when (mood) {
    Mood.NEUTRAL -> R.raw.cat_neutral
    Mood.CONTENT -> R.raw.cat_content
    Mood.HAPPY -> R.raw.cat_happy
    Mood.VERY_HAPPY -> R.raw.cat_very_happy
}