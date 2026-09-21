package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.mhealthcat.elementsAndClasses.DataType
import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.data.forms.SocialForm
import com.example.mhealthcat.data.forms.SportForm
import com.example.mhealthcat.data.forms.WellbeingForm
import com.example.mhealthcat.data.repositories.MhealthDataRepositories
import com.example.mhealthcat.testData.TestData
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.RetroPurple
import com.example.mhealthcat.ui.theme.RetroRed
import com.example.mhealthcat.ui.theme.RetroTeal
import com.example.mhealthcat.ui.theme.RetroYellow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DataDisplayViewModel : ViewModel() {

    private val sleepRepository = MhealthDataRepositories.sleepRepository
    private val socialRepository = MhealthDataRepositories.socialRepository
    private val sportRepository = MhealthDataRepositories.sportRepository
    private val wellbeingRepository = MhealthDataRepositories.wellbeingRepository

    private val _selectedDataType = MutableStateFlow(DataType.SLEEP)

    private val _sliceColors = listOf(RetroRed, RetroYellow, RetroTeal, RetroPixelBorder, RetroPurple)
    private val _graphView = MutableStateFlow(true)


    val listOfSleepData: StateFlow<List<SleepForm>> = sleepRepository.items
    val listOfSocialData: StateFlow<List<SocialForm>> = socialRepository.items
    val listOfSportData: StateFlow<List<SportForm>> = sportRepository.items
    val listOfWellbeingData: StateFlow<List<WellbeingForm>> = wellbeingRepository.items

    val graphView: MutableStateFlow<Boolean> = _graphView

    val selectedDataType: MutableStateFlow<DataType> = _selectedDataType


    fun formatTimestamp(createdAt: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - createdAt

        val minutes = diff / (1000 * 60)
        val hours = diff / (1000 * 60 * 60)
        val days = diff / (1000 * 60 * 60 * 24)

        return when {
            minutes < 60 -> "$minutes min nazaj"
            hours < 24 -> "$hours h nazaj"
            days <= 31 -> "$days dni nazaj"
            else ->
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(
                    Date(createdAt)
                )
        }
    }

    fun toggleGraphView(index: Int) {
        _graphView.value = index == 0
    }

    fun changeDataType(displayName: String) {
        _selectedDataType.value = DataType.entries.first { it.displayName == displayName }
    }

    fun dataTypesAsStringList(): List<String> {
        return DataType.entries.map { it.displayName }
    }

    fun returnSleepRatings(): Map<Int, Int> =
        sleepRepository.items.value.groupingBy { it.rating }.eachCount()

    fun returnSocialRatings(): Map<Int, Int> =
        socialRepository.items.value.groupingBy { it.rating }.eachCount()

    fun returnSportRatings(): Map<Int, Int> =
        sportRepository.items.value.groupingBy { it.rating }.eachCount()

    fun returnWellbeingRatings(): Map<Int, Int> =
        wellbeingRepository.items.value.groupingBy { it.rating }.eachCount()

    private fun buildChartData(data: Map<Int, Int>): PieChartData = PieChartData(
        slices = (1..5).map { rating ->
            PieChartData.Slice(
                label = "$rating ⭐",
                value = data[rating]?.toFloat() ?: 0f,
                color = _sliceColors[rating - 1]
            )
        },
        plotType = PlotType.Donut
    )
    fun returnSleepChartData()     = buildChartData(returnSleepRatings())
    fun returnSocialChartData()    = buildChartData(returnSocialRatings())
    fun returnSportChartData()     = buildChartData(returnSportRatings())
    fun returnWellbeingChartData() = buildChartData(returnWellbeingRatings())

    fun removeSleepItem(item: SleepForm) {
        sleepRepository.remove(item.id)
    }
    fun removeSocialItem(item: SocialForm) {
        socialRepository.remove(item.id)
    }
    fun removeSportItem(item: SportForm) {
        sportRepository.remove(item.id)
    }
    fun removeWellbeingItem(item: WellbeingForm) {
        wellbeingRepository.remove(item.id)
    }

}