package com.example.mhealthcat.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mhealthcat.elementsAndClasses.AppScreen

class NavigationViewModel : ViewModel() {
    private val _isLoggedIn = mutableStateOf(false)
    private val _currentScreen = mutableStateOf(if (_isLoggedIn.value) AppScreen.Home else AppScreen.LogIn)

    val currentScreen: State<AppScreen> = _currentScreen

    fun changeToScreen(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun isLoggedIn(loginStatus: Boolean) {
        _isLoggedIn.value = loginStatus
    }
}