package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.SleepForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SleepViewModel : ViewModel() {
    private val _sleepForm = MutableStateFlow(SleepForm())
    private val _showForm = MutableStateFlow(false)
    private val _showValidationError = MutableStateFlow(false)

    val sleepForm : StateFlow<SleepForm> = _sleepForm.asStateFlow()
    val showForm : StateFlow<Boolean> = _showForm.asStateFlow()

    val showValidationError: StateFlow<Boolean> = _showValidationError.asStateFlow()

    fun toggleShowFormOn() {
        _showForm.value = true
    }

    fun toggleShowFormOff() {
        _showForm.value = false
    }

    fun updateRating(rating: Int) {
        _sleepForm.value = _sleepForm.value.copy(rating = rating)
    }

    fun updateComment(comment: String) {
        _sleepForm.value = _sleepForm.value.copy(comment = comment)
    }

    fun updateTime(hours: Int, minutes: Int) {
        _sleepForm.value = _sleepForm.value.copy(hours = hours, minutes = minutes)
    }

    fun validateForm(): Boolean {
        val form = _sleepForm.value
        return (form.hours > 0 || form.minutes > 0) && form.comment.isNotEmpty()
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

    fun submitForm() {
        toggleShowFormOff()
        clearForm()
    }

    fun clearForm() {
        _sleepForm.value = SleepForm()
        _showValidationError.value = false
    }

    fun cancelForm() {
        clearForm()
        toggleShowFormOff()
    }


}