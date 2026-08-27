package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.SleepForm
import kotlinx.coroutines.flow.MutableStateFlow

class SleepViewModel : ViewModel() {
    private val _sleepForm = MutableStateFlow(SleepForm())
    private val _showForm = MutableStateFlow(false)

    val sleepForm : MutableStateFlow<SleepForm> = _sleepForm
    val showForm : MutableStateFlow<Boolean> = _showForm

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

    fun submitForm() {
        if (validateForm()) {
            println("Podatki o spancu so shranjeni.")
            toggleShowFormOff()
            clearForm()
        } else println("Narobe izpolnjeni podatki o spancu.")

    }

    fun clearForm() {
        _sleepForm.value = SleepForm()
    }

    fun cancelForm() {
        clearForm()
        toggleShowFormOff()
    }


}