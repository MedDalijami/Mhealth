package com.example.mhealthcat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

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