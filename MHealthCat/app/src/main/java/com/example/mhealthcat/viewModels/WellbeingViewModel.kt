package com.example.mhealthcat.viewModels


import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.WellbeingForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WellbeingViewModel: ViewModel() {
    private val _wellbeingForm = MutableStateFlow(WellbeingForm())
    private val _showForm = MutableStateFlow(false)
    private val _showValidationError = MutableStateFlow(false)

    val showForm: StateFlow<Boolean> = _showForm.asStateFlow()
    val wellbeingForm: StateFlow<WellbeingForm> = _wellbeingForm.asStateFlow()
    val showValidationError: StateFlow<Boolean> = _showValidationError.asStateFlow()


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
        _showValidationError.value = false
    }

    fun validateForm(): Boolean {
        val form = _wellbeingForm.value
        return form.generalFeelings.isNotEmpty() && form.generalFears.isNotEmpty() && form.somethingGoodThatHappened.isNotEmpty()
    }

    fun submitForm() {
        toggleShowFormOff()
        clearForm()
    }

    fun attemptSubmit(): Boolean {
        return if (validateForm()) {
            _showValidationError.value = false
            true
        } else {
            _showValidationError.value = true
            false
        }
    }
    fun cancelForm() {
        clearForm()
        toggleShowFormOff()
    }
}