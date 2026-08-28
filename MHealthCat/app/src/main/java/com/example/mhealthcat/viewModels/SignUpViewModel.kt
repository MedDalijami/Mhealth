package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import android.net.Uri
import com.example.mhealthcat.forms.SignUpForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel: ViewModel() {
    private val _signUpForm = MutableStateFlow(SignUpForm())
    private val _showError = MutableStateFlow(false)
    private val _allowSubmit = MutableStateFlow(false)

    val signUpForm: StateFlow<SignUpForm> = _signUpForm.asStateFlow()
    val showError: StateFlow<Boolean> = _showError.asStateFlow()
    val allowSubmit: StateFlow<Boolean> = _allowSubmit.asStateFlow()

    fun toggleShowErrorOn() {
        _showError.value = true
    }
    fun toggleShowErrorOff() {
        _showError.value = false
    }

    fun toggleAllowSubmitOn() {
        _allowSubmit.value = true
    }

    fun toggleAllowSubmitOff() {
        _allowSubmit.value = false
    }

    fun updateName(name: String) {
        _signUpForm.value = _signUpForm.value.copy(name = name)
        isValidForSubmit()
    }

    fun updateLastName(lastName: String) {
        _signUpForm.value = _signUpForm.value.copy(lastName = lastName)
        isValidForSubmit()
    }

    fun updateEmail(email: String) {
        _signUpForm.value = _signUpForm.value.copy(email = email)
        isValidForSubmit()
    }


    fun updatePassword(password: String) {
        _signUpForm.value = _signUpForm.value.copy(password = password)
        isValidForSubmit()
    }

    fun updatePasswordRepeat(passwordRepeat: String) {
        _signUpForm.value = _signUpForm.value.copy(passwordRepeat = passwordRepeat)
        isValidForSubmit()
    }

    fun isValidEmail(): Boolean {
        val form = _signUpForm.value
        return form.email.contains("@") && form.email.contains(".")
    }

    fun isValidPassword() : Boolean {
        val form = _signUpForm.value
        return form.password.length >= 8 && form.password.any { it.isDigit() }
    }

    fun isValidPasswordRepeat() : Boolean {
        val form = _signUpForm.value
        return form.passwordRepeat == form.password && isValidPassword()
    }

    private fun isValidForSubmit() {
        val form = _signUpForm.value
        return if (form.name.isNotEmpty() && form.lastName.isNotEmpty() && isValidEmail()
            && isValidPassword() && isValidPasswordRepeat()) {
            toggleAllowSubmitOn()
        } else {
            toggleAllowSubmitOff()
        }
    }

    fun updateProfilePicture(uri: Uri?) {
        _signUpForm.value = _signUpForm.value.copy(profilePictureUri = uri)
    }

    fun signUp(): Boolean {
        return if (userDoesNotExist(_signUpForm.value.email)) {
            println("Uspešna registracija.")
            toggleShowErrorOff()
            clearForm()
            true

        } else {
            println("Neuspešna registracija.")
            toggleShowErrorOn()
            false
        }
    }

    private fun clearForm() {
        _signUpForm.value = SignUpForm()
    }

    private fun userDoesNotExist(email: String): Boolean {
        return email != "testni@uporabnik.si"
    }


}