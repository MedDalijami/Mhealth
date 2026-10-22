package com.example.mhealthcat.viewModels


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mhealthcat.data.repositories.AuthRepository
import com.example.mhealthcat.data.repositories.ProfilePictureStore
import com.example.mhealthcat.models.UserProfile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

// AndroidViewModel gives access to the app context, which is needed to read and cache pictures

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModel(application: Application) : AndroidViewModel(application) {

    // The actual data of logged-in user, loaded from Firestore (users/{uid})
    private val _userProfileActual = MutableStateFlow(UserProfile())


    // Copy of the data that can be edited
    private val _userProfile = MutableStateFlow( _userProfileActual.value)

    private val _currentPasswordInput = MutableStateFlow<String>("")

    private val _deleteAccountPassword = MutableStateFlow("")


    private val _editingProfile = MutableStateFlow(false)
    private val _editingPassword = MutableStateFlow(false)


    private val _allowEditPasswordSubmit = MutableStateFlow(false)
    private val _newPassword = MutableStateFlow("")
    private val _newPasswordRepeat = MutableStateFlow("")
    private val _passwordChangeError = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)



    val userProfile: StateFlow<UserProfile> = _userProfile
    val currentPasswordInput: StateFlow<String> = _currentPasswordInput
    val editingProfile: StateFlow<Boolean> = _editingProfile
    val editingPassword: StateFlow<Boolean> = _editingPassword

    val allowEditPasswordSubmit: StateFlow<Boolean> = _allowEditPasswordSubmit

    val newPassword: StateFlow<String> = _newPassword
    val deleteAccountPassword: StateFlow<String> = _deleteAccountPassword

    val newPasswordRepeat: StateFlow<String> = _newPasswordRepeat
    val passwordChangeError: StateFlow<String?> = _passwordChangeError
    val isLoading: StateFlow<Boolean> = _isLoading


    init {
        viewModelScope.launch {
            AuthRepository.currentUser
                .flatMapLatest { user ->
                    if (user == null) flowOf(UserProfile()) else AuthRepository.profile(user)
                }
                .collect { profile ->
                    // Turn the stored thumbnail into a Uri the image composables can show
                    val pictureUri = profile.profilePictureData?.let { data ->
                        runCatching {
                            ProfilePictureStore.toCachedUri(getApplication(), profile.id, data)
                        }.getOrNull()
                    }
                    _userProfileActual.value = profile.copy(profilePictureUri = pictureUri)
                    if (!_editingProfile.value) {
                        _userProfile.value = _userProfileActual.value
                    }
                }
        }
    }


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
        _currentPasswordInput.value = ""
        _newPassword.value = ""
        _newPasswordRepeat.value = ""
        _passwordChangeError.value = null
        _allowEditPasswordSubmit.value = false
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
        val password = currentPasswordInput.value
        return password.length >= 8 && password.any { it.isDigit() }
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

    fun updateDeleteAccountPassword(password: String) {
        _deleteAccountPassword.value = password
    }

    fun clearDeleteAccountPassword() {
        _deleteAccountPassword.value = ""
    }

    fun saveUserProfile() {
        val newPictureUri = _userProfile.value.profilePictureUri
            .takeIf { it != _userProfileActual.value.profilePictureUri }

        _userProfileActual.value = _userProfileActual.value.copy(
            name = _userProfile.value.name,
            lastName = _userProfile.value.lastName,
            profilePictureUri = _userProfile.value.profilePictureUri
        )
        _userProfile.value = _userProfileActual.value
        viewModelScope.launch {
            try {
                AuthRepository.updateProfile(_userProfileActual.value.name, _userProfileActual.value.lastName)
                if (newPictureUri != null) {
                    AuthRepository.updateProfilePicture(ProfilePictureStore.encode(getApplication(), newPictureUri))
                }
            } catch (e: Exception) {
                println("Shranjevanje profila ni uspelo: ${e.message}")
            }
        }
    }

    fun updateCurrentPassword(password: String) {
        _currentPasswordInput.value = password
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

    fun toggleAllowEditPasswordSubmit() {
        _allowEditPasswordSubmit.value = isValidPassword() && isValidNewPassword() && isValidPasswordRepeat()
    }

    fun changePassword() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                AuthRepository.changePassword(_currentPasswordInput.value, _newPassword.value)
                toggleEditingPasswordOff()
            } catch (e: Exception) {
                _passwordChangeError.value =
                    AuthRepository.errorMessage(e, wrongCredentialsText = "Vpisali ste napačno trenutno geslo.")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logOut() {
        AuthRepository.logOut()
        clearUserProfile()
        clearDeleteAccountPassword()
        clearPasswords()
        _editingProfile.value = false
        _editingPassword.value = false
    }

    fun deleteUserProfile(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                AuthRepository.deleteAccount(_deleteAccountPassword.value)
                logOut()
                onSuccess()
            } catch (e: Exception) {
                onError(AuthRepository.errorMessage(e, wrongCredentialsText = "Vpisano geslo ni pravilno"))
            } finally {
                _isLoading.value = false
            }
        }
    }

}
