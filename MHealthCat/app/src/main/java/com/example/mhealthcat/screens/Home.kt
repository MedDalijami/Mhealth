package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mhealthcat.R
import com.example.mhealthcat.elementsAndClasses.BackgroundAnimation

@Composable
fun Home() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        BackgroundAnimation(
            videoResId = R.raw.cat_home,
            modifier = Modifier.size(500.dp)
        )
    }

}


