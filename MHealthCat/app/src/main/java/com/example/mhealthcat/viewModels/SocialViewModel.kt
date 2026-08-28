package com.example.mhealthcat.viewModels

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.SocialForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialViewModel: ViewModel() {
    private val _socialForm = MutableStateFlow(SocialForm())
    private val _showForm = MutableStateFlow(false)

    private val _showValidationError = MutableStateFlow(false)


    val socialForm: StateFlow<SocialForm> = _socialForm.asStateFlow()
    val showForm: StateFlow<Boolean> = _showForm.asStateFlow()

    val showValidationError: StateFlow<Boolean> = _showValidationError.asStateFlow()

    val peopleList = listOf("Prijatelji", "Partner/ka" ,"Družina", "Neznanci, Sodelavci", "Drugo")


    fun toggleShowFormOn() {
        _showForm.value = true
    }

    fun toggleShowFormOff() {
        _showForm.value = false
    }

    fun updateSocialInteraction(socialInteraction: String) {
        _socialForm.value = _socialForm.value.copy(socialInteraction = socialInteraction)
    }

    fun isOther(): Boolean {
        return _socialForm.value.socialInteraction == "Drugo"
    }

    fun updatePeople(people: String) {
        _socialForm.value = _socialForm.value.copy(people = people)
    }

    fun  updateComment(comment: String) {
        _socialForm.value = _socialForm.value.copy(comment = comment)
    }

    fun increaseNumberOfPeople() {
        _socialForm.value = _socialForm.value.copy(numberOfPeople = _socialForm.value.numberOfPeople + 1)
    }

    fun decreaseNumberOfPeople() {
        _socialForm.value = _socialForm.value.copy(numberOfPeople = _socialForm.value.numberOfPeople - 1)
    }

    fun updateRating(rating: Int) {
        _socialForm.value = _socialForm.value.copy(rating = rating)
    }

    fun updateTime(hours: Int, minutes: Int) {
        _socialForm.value = _socialForm.value.copy(hours = hours, minutes = minutes)
    }

    fun clearForm() {
        _socialForm.value = SocialForm()
        _showValidationError.value = false
    }

    fun validateForm(): Boolean {
        val form = _socialForm.value
        return form.people.isNotEmpty() && form.comment.isNotEmpty() && (form.hours > 0 || form.minutes > 0)
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

    fun cancelForm() {
        clearForm()
        toggleShowFormOff()
    }



}