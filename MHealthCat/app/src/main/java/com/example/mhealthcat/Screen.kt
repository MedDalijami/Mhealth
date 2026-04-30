package com.example.mhealthcat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mhealthcat.Screens.Login


// Functions like pseudo "State machine"
enum class AppScreen {
    Home, LogIn, SignUp, Sleep, Social, Sport, Wellbeing, Settings, User
}

@Composable
fun Screen(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(AppScreen.LogIn) }

    Box(
        modifier = modifier
            .fillMaxSize()

    )
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(5.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            when (currentScreen)
            {
                AppScreen.Home -> Text("Home")
                AppScreen.LogIn -> Login()
                AppScreen.SignUp -> Text("SignUp")
                AppScreen.Sleep -> Text("Sleep")
                AppScreen.Social -> Text("Social")
                AppScreen.Sport -> Text("Sports")
                AppScreen.Wellbeing -> Text("Wellbeing")
                AppScreen.Settings -> Text("Settings")
                AppScreen.User -> Text("User")

            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(7.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            // Menu is always visible apart from when user is on LogIn/ SignUp
            if (currentScreen != AppScreen.LogIn && currentScreen != AppScreen.SignUp){
                MenuBar(onNavigate = { currentScreen = it })
            }

        }
    }
}