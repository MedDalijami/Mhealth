package com.example.mhealthcat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mhealthcat.R

// Set of Material typography styles to start with

// Used pixelFont
val pixelFont = FontFamily(Font(R.font.press_start_2p))
val roboto = FontFamily(Font(R.font.roboto_regular))
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = pixelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 9.sp,
        lineHeight = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = pixelFont,
        fontSize = 8.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = pixelFont,
        fontSize = 7.sp,
        lineHeight = 14.sp,
    ),

)