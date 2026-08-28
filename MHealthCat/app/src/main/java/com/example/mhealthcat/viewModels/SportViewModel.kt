package com.example.mhealthcat.viewModels


import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.SportForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SportViewModel: ViewModel() {
    private val _sportForm = MutableStateFlow(SportForm())
    private val _showForm = MutableStateFlow(false)

    private val _showValidationError = MutableStateFlow(false)

    val sportForm: StateFlow<SportForm> = _sportForm.asStateFlow()
    val showForm: StateFlow<Boolean> = _showForm.asStateFlow()
    val showValidationError: StateFlow<Boolean> = _showValidationError.asStateFlow()

    fun toggleShowFormOn() {
        _showForm.value = true
    }

    fun toggleShowFormOff() {
        _showForm.value = false
    }

    fun updateActivity(activity: String) {
        _sportForm.value = _sportForm.value.copy(activity = activity)
    }

    fun updateComment(comment: String) {
        _sportForm.value = _sportForm.value.copy(comment = comment)
    }

    fun updateRating(rating: Int) {
        _sportForm.value = _sportForm.value.copy(rating = rating)
    }

    fun updateTime(hours: Int, minutes: Int) {
        _sportForm.value = _sportForm.value.copy(hours = hours, minutes = minutes)
    }

    fun clearForm() {
        _sportForm.value = SportForm()
        _showValidationError.value = false
    }

    fun validateForm(): Boolean {
        val form = _sportForm.value
        return form.activity.isNotEmpty() && form.comment.isNotEmpty() && (form.hours > 0 || form.minutes > 0)
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