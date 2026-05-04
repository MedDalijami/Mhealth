package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mhealthcat.functionsAndLibraries.CreateAlert
import com.example.mhealthcat.functionsAndLibraries.CreateTimeDial
import com.example.mhealthcat.ui.theme.MHealthCatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sleep() {
    //To test the time data
    var showTime by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0,
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {}

        CreateTimeDial(
            onClick = {showTime = true},
            setButtonText = "Zabeleži spanec",
            timePickerState = timePickerState
        )
    }

    if (showTime) {
        CreateAlert(
            onDismissRequest = {
                timePickerState.hour = 0
                timePickerState.minute = 0
                showTime = false
            },
            onConfirm = {
                showTime = false
            },
            alertTitle = "Ali želite zabeležiti spanec?",
            alertText = "Vaš celoten čas spanca znaša: %02d ur in %02d minut".format(
                timePickerState.hour,
                timePickerState.minute
            )
        )
    }

}


@Preview(showBackground = true)
@Composable
fun SleepPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Sleep()
        }
    }
}