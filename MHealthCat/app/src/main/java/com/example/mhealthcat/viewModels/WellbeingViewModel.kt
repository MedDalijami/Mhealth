package com.example.mhealthcat.viewModels

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.WellbeingForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WellbeingViewModel: ViewModel() {
    private val _wellbeingForm = MutableStateFlow(WellbeingForm())
    private val _showForm = MutableStateFlow(false)

    val showForm: StateFlow<Boolean> = _showForm.asStateFlow()
    val wellbeingForm: StateFlow<WellbeingForm> = _wellbeingForm.asStateFlow()

    fun toggleShowFormOn() {
        _showForm.value = true
    }

    fun toggleShowFormOff() {
        _showForm.value = false
    }

    fun updateRating(rating: Int) {
        _wellbeingForm.value = _wellbeingForm.value.copy(rating = rating)
    }

    fun updateGeneralFeelings(generalFeelings: String) {
        _wellbeingForm.value = _wellbeingForm.value.copy(generalFeelings = generalFeelings)
    }

    fun updateGeneralFears(generalFears: String) {
        _wellbeingForm.value = _wellbeingForm.value.copy(generalFears = generalFears)
    }

    fun updateSomethingGoodThatHappened(somethingGoodThatHappened: String) {
        _wellbeingForm.value = _wellbeingForm.value.copy(somethingGoodThatHappened = somethingGoodThatHappened)
    }

    fun clearForm() {
        _wellbeingForm.value = WellbeingForm()
    }

    fun validateForm(): Boolean {
        val form = _wellbeingForm.value
        return form.generalFeelings.isNotEmpty() && form.generalFears.isNotEmpty() && form.somethingGoodThatHappened.isNotEmpty()
    }

    fun submitForm() {
        if (validateForm()) {
            println("Podatki o počutju so shranjeni")
            toggleShowFormOff()
            clearForm()
        }
        else println("Narobe izpolnjeni podatki o počutju")
    }

}