package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import com.example.mhealthcat.forms.LogInForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LogInViewModel : ViewModel(){
    private val _showError = MutableStateFlow(false)
    private val _logInForm = MutableStateFlow(LogInForm())

    private val _allowSubmit = MutableStateFlow(false)



    val showError: StateFlow<Boolean> = _showError
    val logInForm: StateFlow<LogInForm> = _logInForm

    val allowSubmit: StateFlow<Boolean> = _allowSubmit


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

    fun updateEmail(email: String) {
        _logInForm.value = _logInForm.value.copy(email = email)
        isValidForSubmit()
    }

    fun updatePassword(password: String) {
        _logInForm.value = _logInForm.value.copy(password = password)
        isValidForSubmit()
    }

    fun isValidEmail(): Boolean {
        val form = _logInForm.value
        return form.email.contains("@") && form.email.contains(".")
    }

    fun isValidPassword() : Boolean {
        val form = _logInForm.value
        return form.password.length >= 8 && form.password.any { it.isDigit() }
    }

    fun isValidForSubmit() {
        if (isValidEmail() && isValidPassword()) {
            toggleAllowSubmitOn()
        } else {
            toggleAllowSubmitOff()
        }
    }

    fun validateForm(): Boolean {
        val form = _logInForm.value
        return form.email == "testni@uporabnik.si" && form.password == "testni123"

    }

    fun clearForm() {
        _logInForm.value = LogInForm()
    }

    fun logIn(): Boolean {
        return if (validateForm()) {
            println("Uspešna prijava." +
                    _logInForm.value)
            toggleShowErrorOff()
            clearForm()
            true

        } else {
            println("Neuspešna prijava.")
            toggleShowErrorOn()
            clearForm()
            false
        }
    }
}