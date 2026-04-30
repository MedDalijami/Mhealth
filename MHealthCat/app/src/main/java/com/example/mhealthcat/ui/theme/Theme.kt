package com.example.mhealthcat.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppColourScheme = darkColorScheme(
    primary = RetroPurple,
    secondary = RetroTeal,
    tertiary = RetroYellow,
    background = RetroDark,
    surface = RetroDark
)




@Composable
fun MHealthCatTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = AppColourScheme,
        typography = Typography,
        content = content
    )
}