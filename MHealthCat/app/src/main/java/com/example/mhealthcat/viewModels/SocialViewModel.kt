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

    val socialForm: StateFlow<SocialForm> = _socialForm.asStateFlow()
    val showForm: StateFlow<Boolean> = _showForm.asStateFlow()

    val socialInteractionTypeList = listOf("Osebno", "Klic", "Skupinsko", "Drugo")
    val peopleList = listOf("Prijatelji", "Partner/ka" ,"Družina", "Neznanci")


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

    fun updateSocialInteractionOther(socialInteractionOther: String) {
        _socialForm.value = _socialForm.value.copy(socialInteractionOther = socialInteractionOther)
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
    }

    fun validateForm(): Boolean {
        val form = _socialForm.value
        return form.people.isNotEmpty() && form.comment.isNotEmpty() && (form.hours > 0 || form.minutes > 0)
    }

    fun submitForm() {
        if (validateForm()) {
            println("Podatki o druženju so shranjeni.")
            toggleShowFormOff()
            clearForm()
        } else println("Narobe izpolnjeni podatki o druženju.")

    }

}