package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.mhealthcat.data.filters.EntryFilter
import com.example.mhealthcat.data.filters.TimeFilter
import com.example.mhealthcat.elementsAndClasses.DataType
import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.data.forms.SocialForm
import com.example.mhealthcat.data.forms.SportForm
import com.example.mhealthcat.data.forms.WellbeingForm
import com.example.mhealthcat.data.repositories.Entry
import com.example.mhealthcat.data.repositories.MhealthDataRepositories
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.RetroPurple
import com.example.mhealthcat.ui.theme.RetroRed
import com.example.mhealthcat.ui.theme.RetroTeal
import com.example.mhealthcat.ui.theme.RetroYellow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class DataDisplayViewModel : ViewModel() {

    private val sleepRepository = MhealthDataRepositories.sleepRepository
    private val socialRepository = MhealthDataRepositories.socialRepository
    private val sportRepository = MhealthDataRepositories.sportRepository
    private val wellbeingRepository = MhealthDataRepositories.wellbeingRepository

    private val _selectedDataType = MutableStateFlow(DataType.ALL)

    private val _sliceColors = listOf(RetroRed, RetroYellow, RetroTeal, RetroPixelBorder, RetroPurple)
    private val _graphView = MutableStateFlow(true)


    private val _timeFilter = MutableStateFlow(TimeFilter.ALL)
    private val _customPeriod = MutableStateFlow<ClosedRange<LocalDate>?>(null)
    private val _ratingRange = MutableStateFlow(EntryFilter.ALL_RATINGS)

    val timeFilter: StateFlow<TimeFilter> = _timeFilter.asStateFlow()
    val customPeriod: StateFlow<ClosedRange<LocalDate>?> = _customPeriod.asStateFlow()
    val ratingRange: StateFlow<IntRange> = _ratingRange.asStateFlow()

    private fun <T : Entry> filtered(items: StateFlow<List<T>>): StateFlow<List<T>> =
        combine(items, _timeFilter, _customPeriod, _ratingRange) { list, time, custom, ratings ->
            EntryFilter.apply(list, EntryFilter.period(time, custom), ratings)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, items.value)

    val listOfSleepData: StateFlow<List<SleepForm>> = filtered(sleepRepository.items)
    val listOfSocialData: StateFlow<List<SocialForm>> = filtered(socialRepository.items)
    val listOfSportData: StateFlow<List<SportForm>> = filtered(sportRepository.items)
    val listOfWellbeingData: StateFlow<List<WellbeingForm>> = filtered(wellbeingRepository.items)
    val graphView: MutableStateFlow<Boolean> = _graphView
    val selectedDataType: MutableStateFlow<DataType> = _selectedDataType

    fun changeTimeFilter(displayName: String) {
        _timeFilter.value = TimeFilter.entries.first { it.displayName == displayName }
    }

    fun setCustomPeriod(startMillis: Long, endMillis: Long) {
        val start = Instant.ofEpochMilli(startMillis).atZone(ZoneOffset.UTC).toLocalDate()
        val end = Instant.ofEpochMilli(endMillis).atZone(ZoneOffset.UTC).toLocalDate()
        _customPeriod.value = start..end
        _timeFilter.value = TimeFilter.CUSTOM    }

    fun timeFilterText(time: TimeFilter, period: ClosedRange<LocalDate>?): String {
        if (time != TimeFilter.CUSTOM || period == null) return time.displayName
        val format = DateTimeFormatter.ofPattern("d. M. yyyy")
        return "${period.start.format(format)} – ${period.endInclusive.format(format)}"
    }

    fun changeRatingRange(min: Int, max: Int) {
        _ratingRange.value = min..max
    }
    fun  ratingRangeText(range: IntRange): String {
        return if (range.first == range.last) {
            "${range.first} ⭐"
        } else {
            "${range.first} - ${range.last} ⭐"
        }
    }

    fun timeFiltersAsStringList(): List<String> = TimeFilter.entries.map { it.displayName }



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
        listOfSleepData.value.groupingBy { it.rating }.eachCount()

    fun returnSocialRatings(): Map<Int, Int> =
        listOfSocialData.value.groupingBy { it.rating }.eachCount()

    fun returnSportRatings(): Map<Int, Int> =
        listOfSportData.value.groupingBy { it.rating }.eachCount()

    fun returnWellbeingRatings(): Map<Int, Int> =
        listOfWellbeingData.value.groupingBy { it.rating }.eachCount()

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

    fun returnAllCategoriesChartData(): PieChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice(DataType.SLEEP.displayName, listOfSleepData.value.size.toFloat(), _sliceColors[0]),
            PieChartData.Slice(DataType.SOCIAL.displayName, listOfSocialData.value.size.toFloat(), _sliceColors[1]),
            PieChartData.Slice(DataType.SPORT.displayName, listOfSportData.value.size.toFloat(), _sliceColors[2]),
            PieChartData.Slice(DataType.WELLBEING.displayName, listOfWellbeingData.value.size.toFloat(), _sliceColors[3])
        ),
        plotType = PlotType.Donut
    )

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