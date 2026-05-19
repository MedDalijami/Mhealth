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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.ElementsAndClasses.AppScreen
import com.example.mhealthcat.screens.LogIn
import com.example.mhealthcat.screens.SignUp
import com.example.mhealthcat.screens.Sleep
import com.example.mhealthcat.screens.Social
import com.example.mhealthcat.screens.Sports
import com.example.mhealthcat.screens.Wellbeing
import com.example.mhealthcat.viewModels.NavigationViewModel


@Composable
fun Screen(modifier: Modifier = Modifier) {
    val navigationViewModel: NavigationViewModel = viewModel()
    val currentScreen by navigationViewModel.currentScreen



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
            when (currentScreen) {
                AppScreen.Home -> Text("")

                AppScreen.LogIn -> LogIn()

                AppScreen.SignUp -> SignUp()

                AppScreen.Sleep -> Sleep()

                AppScreen.Social -> Social()

                AppScreen.Sport -> Sports()

                AppScreen.Wellbeing -> Wellbeing()

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
            if (currentScreen != AppScreen.LogIn && currentScreen != AppScreen.SignUp) {
                MenuBar()
            }

        }
    }
}