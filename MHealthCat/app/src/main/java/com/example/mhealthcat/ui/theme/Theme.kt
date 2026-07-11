package com.example.mhealthcat.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppColourScheme = lightColorScheme(
    primary = SagePrimary,
    onPrimary = Color.White,
    secondary = CoralAccent,
    onSecondary = Color.White,
    tertiary = WarmGold,
    onTertiary = InkPrimary,
    background = CreamBackground,
    onBackground = InkPrimary,
    surface = CreamBackground,
    onSurface = InkPrimary,
    surfaceVariant = MistSurface,
    onSurfaceVariant = StoneSecondary,
    error = RustError,
    onError = Color.White
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