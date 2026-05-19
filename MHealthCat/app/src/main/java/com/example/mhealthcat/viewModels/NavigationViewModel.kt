package com.example.mhealthcat.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.mhealthcat.ElementsAndClasses.AppScreen

class NavigationViewModel : ViewModel() {
    private val _currentScreen = mutableStateOf(AppScreen.Home)
    val currentScreen: State<AppScreen> = _currentScreen

    fun changeToScreen(screen: AppScreen) {
        _currentScreen.value = screen
    }
}