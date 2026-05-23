package com.example.mhealthcat.viewModels


import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mhealthcat.models.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel : ViewModel() {

    // The actual data of logged-in user
    private val _userProfileActual = MutableStateFlow(UserProfile(
        email = "testni@uporabnik.si",
        name = "Jana",
        lastName = "Novak",
        password = "testni123"
    ))


    // Copy of the data that can be edited
    private val _userProfile = MutableStateFlow( _userProfileActual.value.copy(password = ""))

    private val _editingProfile = MutableStateFlow(false)
    private val _editingPassword = MutableStateFlow(false)


    private val _allowEditPasswordSubmit = MutableStateFlow(false)
    private val _newPassword = MutableStateFlow("")
    private val _newPasswordRepeat = MutableStateFlow("")



    val userProfile: StateFlow<UserProfile> = _userProfile
    val editingProfile: StateFlow<Boolean> = _editingProfile
    val editingPassword: StateFlow<Boolean> = _editingPassword

    val allowEditPasswordSubmit: StateFlow<Boolean> = _allowEditPasswordSubmit

    val newPassword: StateFlow<String> = _newPassword

    val newPasswordRepeat: StateFlow<String> = _newPasswordRepeat


    fun resetUserProfile() {
        _userProfile.value = _userProfileActual.value
    }

    fun toggleEditingProfileOn() {
        _editingProfile.value = true
    }

    fun toggleEditingProfileOff() {
        _editingProfile.value = false
    }

    fun clearPasswords() {
        _userProfile.value = _userProfile.value.copy(password = "")
        _newPassword.value = ""
        _newPasswordRepeat.value = ""
    }

    fun toggleEditingPasswordOn() {
        _editingPassword.value = true
        clearPasswords()
    }

    fun toggleEditingPasswordOff() {
        _editingPassword.value = false
        clearPasswords()

    }

    fun updateProfilePicture(selectedUri: Uri?) {
        _userProfile.value = _userProfile.value.copy(profilePictureUri = selectedUri)
    }

    fun isValidEmail(): Boolean {
        val form = _userProfile.value
        return form.email.contains("@") && form.email.contains(".")
    }

    fun isValidPassword() : Boolean {
        val form = _userProfile.value
        return form.password.length >= 8 && form.password.any { it.isDigit() }
    }

    fun isValidNewPassword() : Boolean {
        return _newPassword.value.length >= 8 && _newPassword.value.any { it.isDigit() }
    }

    fun isValidPasswordRepeat() : Boolean {
        return _newPassword.value == _newPasswordRepeat.value
    }

    fun isValidForEditProfile() : Boolean {
        val form = _userProfile.value
        return form.name.isNotEmpty() && form.lastName.isNotEmpty() && isValidEmail()
    }

    fun updateName(name: String) {
        _userProfile.value = _userProfile.value.copy(name = name)
    }

    fun updateLastName(lastName: String) {
        _userProfile.value = _userProfile.value.copy(lastName = lastName)
    }

    fun updateEmail (email: String) {
        _userProfile.value = _userProfile.value.copy(email = email)
    }

    fun clearUserProfile() {
        _userProfile.value = UserProfile()
    }

    fun saveUserProfile() {
        _userProfileActual.value = _userProfileActual.value.copy(
            name = _userProfile.value.name,
            lastName = _userProfile.value.lastName,
            email = _userProfile.value.email,
            profilePictureUri = _userProfile.value.profilePictureUri
        )
    }

    fun updateCurrentPassword(password: String) {
        _userProfile.value = _userProfile.value.copy(password = password)
        toggleAllowEditPasswordSubmit()
    }

    fun updateNewPassword(newPassword: String) {
        _newPassword.value = newPassword
        toggleAllowEditPasswordSubmit()
    }

    fun updateNewPasswordRepeat(newPasswordRepeat: String) {
        _newPasswordRepeat.value = newPasswordRepeat
        toggleAllowEditPasswordSubmit()
    }


    fun isPasswordCorrect(): Boolean {
        return _userProfile.value.password == _userProfileActual.value.password
    }

    fun saveNewPassword() {
        _userProfileActual.value = _userProfileActual.value.copy(password = _newPassword.value)

    }

    fun toggleAllowEditPasswordSubmit() {
        _allowEditPasswordSubmit.value = isValidPassword() && isValidNewPassword() && isValidPasswordRepeat()
    }

}