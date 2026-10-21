package com.example.mhealthcat.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mhealthcat.data.forms.LogInForm
import com.example.mhealthcat.data.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel(){
    private val _showError = MutableStateFlow(false)
    private val _errorText = MutableStateFlow("")
    private val _logInForm = MutableStateFlow(LogInForm())

    private val _allowSubmit = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)



    val showError: StateFlow<Boolean> = _showError.asStateFlow()
    val errorText: StateFlow<String> = _errorText.asStateFlow()
    val logInForm: StateFlow<LogInForm> = _logInForm.asStateFlow()

    val allowSubmit: StateFlow<Boolean> = _allowSubmit.asStateFlow()
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


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

    private fun isValidForSubmit() {
        if (isValidEmail() && isValidPassword()) {
            toggleAllowSubmitOn()
        } else {
            toggleAllowSubmitOff()
        }
    }

    private fun clearForm() {
        _logInForm.value = LogInForm()
        toggleAllowSubmitOff()
    }
    fun logIn(onSuccess: () -> Unit) {
        val form = _logInForm.value
        viewModelScope.launch {
            _isLoading.value = true
            try {
                AuthRepository.logIn(form.email.trim(), form.password)
                toggleShowErrorOff()
                clearForm()
                onSuccess()
            } catch (e: Exception) {
                _errorText.value = AuthRepository.errorMessage(e)
                toggleShowErrorOn()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
