package com.example.mhealthcat.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.example.mhealthcat.data.forms.SignUpForm
import com.example.mhealthcat.data.repositories.AuthRepository
import com.example.mhealthcat.data.repositories.ProfilePictureStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// AndroidViewModel gives access to the app context, which is needed to read the picked picture
class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val _signUpForm = MutableStateFlow(SignUpForm())
    private val _showError = MutableStateFlow(false)
    private val _errorText = MutableStateFlow("")
    private val _allowSubmit = MutableStateFlow(false)
    private val _isLoading = MutableStateFlow(false)

    val signUpForm: StateFlow<SignUpForm> = _signUpForm.asStateFlow()
    val showError: StateFlow<Boolean> = _showError.asStateFlow()
    val errorText: StateFlow<String> = _errorText.asStateFlow()
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

    // Creates the account in Firebase; the user is logged in afterwards, so onSuccess can go to Home
    fun signUp(onSuccess: () -> Unit) {
        val form = _signUpForm.value
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // A picture that can't be read shouldn't block the sign-up, so it is simply left out
                val picture = form.profilePictureUri?.let { uri ->
                    runCatching { ProfilePictureStore.encode(getApplication(), uri) }.getOrNull()
                }
                AuthRepository.signUp(
                    form.name.trim(),
                    form.lastName.trim(),
                    form.email.trim(),
                    form.password,
                    picture
                )
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

    private fun clearForm() {
        _signUpForm.value = SignUpForm()
        toggleAllowSubmitOff()
    }


}
