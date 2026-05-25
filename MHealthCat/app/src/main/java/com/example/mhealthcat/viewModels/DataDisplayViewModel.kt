package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.mhealthcat.ElementsAndClasses.DataType
import com.example.mhealthcat.forms.SleepForm
import com.example.mhealthcat.forms.SocialForm
import com.example.mhealthcat.forms.SportForm
import com.example.mhealthcat.forms.WellbeingForm
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
    private val _listOfSleepData = MutableStateFlow(TestData.sleepData.sortedByDescending { it.createdAt })
    private val _listOfSocialData = MutableStateFlow(TestData.socialData.sortedByDescending { it.createdAt })
    private val _listOfSportData = MutableStateFlow(TestData.sportData.sortedByDescending { it.createdAt })
    private val _listOfWellbeingData = MutableStateFlow(TestData.wellbeingData.sortedByDescending { it.createdAt })

    private val _selectedDataType = MutableStateFlow(DataType.SLEEP)

    private val _sliceColors = listOf(RetroRed, RetroYellow, RetroTeal, RetroPixelBorder, RetroPurple)
    private val _graphView = MutableStateFlow(true)


    val listOfSleepData: StateFlow<List<SleepForm>> = _listOfSleepData
    val listOfSocialData: MutableStateFlow<List<SocialForm>> = _listOfSocialData
    val listOfSportData: MutableStateFlow<List<SportForm>> = _listOfSportData
    val listOfWellbeingData: MutableStateFlow<List<WellbeingForm>> = _listOfWellbeingData

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
        _selectedDataType.value = DataType.entries.first() { it.displayName == displayName }
    }

    fun dataTypesAsStringList(): List<String> {
        return DataType.entries.map { it.displayName }
    }

    fun returnSleepRatings(): Map<Int, Int> =
        _listOfSleepData.value.groupingBy { it.rating }.eachCount()

    fun returnSocialRatings(): Map<Int, Int> =
        _listOfSocialData.value.groupingBy { it.rating }.eachCount()

    fun returnSportRatings(): Map<Int, Int> =
        _listOfSportData.value.groupingBy { it.rating }.eachCount()

    fun returnWellbeingRatings(): Map<Int, Int> =
        _listOfWellbeingData.value.groupingBy { it.rating }.eachCount()

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

    fun removeSleepItem(item: SleepForm) { _listOfSleepData.value = _listOfSleepData.value - item }
    fun removeSocialItem(item: SocialForm) { _listOfSocialData.value = _listOfSocialData.value - item }
    fun removeSportItem(item: SportForm) { _listOfSportData.value = _listOfSportData.value - item }
    fun removeWellbeingItem(item: WellbeingForm) { _listOfWellbeingData.value = _listOfWellbeingData.value - item }

}