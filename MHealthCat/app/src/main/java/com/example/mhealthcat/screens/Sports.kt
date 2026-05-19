package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.mhealthcat.ElementsAndClasses.CreateAlert
import com.example.mhealthcat.ElementsAndClasses.CreateCommentBox
import com.example.mhealthcat.ElementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.ElementsAndClasses.CreateStarRating
import com.example.mhealthcat.ElementsAndClasses.CreateTimeDial
import com.example.mhealthcat.forms.SportForm
import com.example.mhealthcat.ui.theme.MHealthCatTheme

@Composable
fun Sports() {
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
                buttonText = "Zabeleži novo športno aktivnost"
            )
        } else {
            // form
            CreateSportForm(onConfirm = {showForm = false})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSportForm(onConfirm: () -> Unit) {
    var sportForm by remember { mutableStateOf(SportForm()) }
    var showAlert by remember { mutableStateOf(false) }



    val timePickerState = rememberTimePickerState(
        initialHour = sportForm.hours,
        initialMinute = sportForm.minutes,
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = sportForm.activity,
            label = "Tip aktivnosti",
            minLines = 1,
            maxLines = 2,
            onValueChange = {
                sportForm = sportForm.copy(activity = it)
            }
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = sportForm.comment,
            label = "Komentar",
            onValueChange = {
                sportForm = sportForm.copy(comment = it)
            }
        )

        CreateStarRating(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            rating = sportForm.rating,
            onRatingChange = {sportForm = sportForm.copy(rating = it)},
            starIconModifier = Modifier.size(40.dp)
        )


        CreateTimeDial(
            onClick = {
                sportForm = sportForm.copy(
                    hours = timePickerState.hour,
                    minutes = timePickerState.minute
                )
                showAlert = true
            },
            setButtonText = "Zabeleži športanje",
            timePickerState = timePickerState
        )
    }

    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                sportForm = SportForm()
                timePickerState.hour = 0
                timePickerState.minute = 0
                showAlert = false
            },
            onConfirm = {
                onConfirm()
                showAlert = false
            },
            alertTitle = "Ali želite zabeležiti športno aktivnost?",
            alertText = ("Vaš celoten čas športanja za aktivnost %s znaša %d ur in %d minut " +
                    "ocenili ste aktivnost z %d ⭐ in pustili komentar: %s").format(
                sportForm.activity,
                sportForm.hours,
                sportForm.minutes,
                sportForm.rating,
                sportForm.comment
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun SportsPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Sports()
        }
    }
}