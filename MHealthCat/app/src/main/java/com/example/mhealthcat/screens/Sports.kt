package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mhealthcat.elementsAndClasses.CreateAlert
import com.example.mhealthcat.elementsAndClasses.CreateCommentBox
import com.example.mhealthcat.elementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.elementsAndClasses.CreateStarRating
import com.example.mhealthcat.elementsAndClasses.CreateTimeDial
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.viewModels.SportViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.R
import com.example.mhealthcat.elementsAndClasses.BackgroundAnimation

@Composable
fun Sports() {

    val sportViewModel: SportViewModel = viewModel()
    val showForm by sportViewModel.showForm.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {


        if (!showForm) {
            // Basic screen
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BackgroundAnimation(
                    modifier = Modifier.size(500.dp),
                    videoResId = R.raw.cat_sports
                )
            }

            CreateOutlineButton(
                onClick = { sportViewModel.toggleShowFormOn() },
                buttonText = "Zabeleži novo športno aktivnost"
            )
        } else {
            // form
            CreateSportForm( sportViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSportForm(
    sportViewModel: SportViewModel
) {

    val sportForm by sportViewModel.sportForm.collectAsState()
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
               sportViewModel.updateActivity(it)
            }
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = sportForm.comment,
            label = "Komentar",
            onValueChange = {
                sportViewModel.updateComment(it)
            }
        )

        CreateStarRating(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            rating = sportForm.rating,
            onRatingChange = {
                sportViewModel.updateRating(it)
            },
            starIconModifier = Modifier.size(40.dp)
        )


        CreateTimeDial(
            onConfirmButtonClicked = {
                sportViewModel.updateTime(hours = timePickerState.hour, minutes = timePickerState.minute)
                showAlert = true
            },
            setButtonText = "Zabeleži",
            timePickerState = timePickerState,
            onCancelButtonClicked = { sportViewModel.cancelForm()}
        )
    }

    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                sportViewModel.clearForm()
                timePickerState.hour = 0
                timePickerState.minute = 0
                showAlert = false
            },
            onConfirm = {
                sportViewModel.submitForm()
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