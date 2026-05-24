package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import com.example.mhealthcat.ElementsAndClasses.DataType
import com.example.mhealthcat.forms.SleepForm
import com.example.mhealthcat.forms.SocialForm
import com.example.mhealthcat.forms.SportForm
import com.example.mhealthcat.forms.WellbeingForm
import com.example.mhealthcat.testData.TestData
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DataDisplayViewModel : ViewModel() {
    private val _listOfSleepData = MutableStateFlow<List<SleepForm>>(emptyList())

    private val _listOfSocialData = MutableStateFlow<List<SocialForm>>(emptyList())

    private val _listOfSportData = MutableStateFlow<List<SportForm>>(emptyList())

    private val _listOfWellbeingData = MutableStateFlow<List<WellbeingForm>>(emptyList())

    private val _selectedDataType = MutableStateFlow(DataType.SLEEP)

    private val _graphView = MutableStateFlow(true)

    val listOfSleepData: MutableStateFlow<List<SleepForm>> = _listOfSleepData

    val listOfSocialData: MutableStateFlow<List<SocialForm>> = _listOfSocialData

    val listOfSportData: MutableStateFlow<List<SportForm>> = _listOfSportData

    val listOfWellbeingData: MutableStateFlow<List<WellbeingForm>> = _listOfWellbeingData

    val graphView: MutableStateFlow<Boolean> = _graphView

    val selectedDataType: MutableStateFlow<DataType> = _selectedDataType

    init {
        _listOfSleepData.value = TestData.sleepData
        _listOfSocialData.value = TestData.socialData
        _listOfSportData.value = TestData.sportData
        _listOfWellbeingData.value = TestData.wellbeingData
    }

    fun formatTimestamp(createdAt: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - createdAt

        val minutes = diff / (1000 * 60)
        val hours   = diff / (1000 * 60 * 60)
        val days    = diff / (1000 * 60 * 60 * 24)

        return when {
            minutes < 60   -> "$minutes min nazaj"
            hours   < 24   -> "$hours h nazaj"
            days    <= 31  -> "$days dni nazaj"
            else           ->
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(createdAt)
            )
        }
    }

    fun toggleGraphView(index: Int)  {
        _graphView.value = index == 0
    }

    fun changeDataType(displayName: String) {
        _selectedDataType.value = DataType.entries.first() { it.displayName == displayName }
    }

    fun dataTypesAsStringList () : List<String> {
        return DataType.entries.map { it.displayName }
    }




}