package com.example.mhealthcat.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.mhealthcat.functionsAndLibraries.CreateCommentBox
import com.example.mhealthcat.functionsAndLibraries.CreateOutlineButton
import com.example.mhealthcat.functionsAndLibraries.CreateStarRating
import com.example.mhealthcat.functionsAndLibraries.CreateTimeDial
import com.example.mhealthcat.functionsAndLibraries.SleepForm
import com.example.mhealthcat.ui.theme.MHealthCatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sleep() {

    var showForm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {


        if (!showForm) {
            // Basic screen
            CreateOutlineButton(
                onClick = { showForm = true },
                buttonText = "Zabeleži novo spanje"
            )
        } else {
            // form
            CreateTimeForm(onConfirm = {showForm = false})
        }
    }


}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CreateTimeForm(
    onConfirm: () -> Unit
) {
    var showAlert by remember { mutableStateOf(false) }
    var sleepForm by remember { mutableStateOf(SleepForm()) }

    val timePickerState = rememberTimePickerState(
        initialHour = sleepForm.hours,
        initialMinute = sleepForm.minutes,
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        CreateStarRating(
            title = "Ocenite svoj spanec",
            rating = sleepForm.rating,
            onRatingChange = {sleepForm = sleepForm.copy(rating = it)},
            starIconModifier = Modifier.size(40.dp)
        )

        CreateCommentBox(
            modifier = Modifier.padding(vertical = 15.dp),
            value = sleepForm.comment,
            label = "Prosim opišite in komentirajte svoj spanec",
            onValueChange = {sleepForm = sleepForm.copy(comment = it)}
        )

        CreateTimeDial(
            onClick = {
                sleepForm = sleepForm.copy(
                    hours = timePickerState.hour,
                    minutes = timePickerState.minute
                )
                showAlert = true
                      },
            setButtonText = "Zabeleži spanec",
            timePickerState = timePickerState
        )
    }

    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                sleepForm = SleepForm()
                timePickerState.hour = 0
                timePickerState.minute = 0
                showAlert = false
            },
            onConfirm = {
                onConfirm()
                showAlert = false
            },
            alertTitle = "Ali želite zabeležiti spanec?",
            alertText = ("Vaš celoten čas spanca znaša %d ur in %d minut " +
                    "ocenili ste spanec z %d ⭐ in pustili komentar: %s").format(
                sleepForm.hours,
                sleepForm.minutes,
                sleepForm.rating,
                sleepForm.comment
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