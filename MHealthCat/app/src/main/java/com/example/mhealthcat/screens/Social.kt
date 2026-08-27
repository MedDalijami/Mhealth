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
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.R
import com.example.mhealthcat.elementsAndClasses.BackgroundAnimation
import com.example.mhealthcat.elementsAndClasses.CreateAlert
import com.example.mhealthcat.elementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.elementsAndClasses.CreateSelectMenu
import com.example.mhealthcat.elementsAndClasses.CreateTextBoxNonError
import com.example.mhealthcat.elementsAndClasses.CreateTimeDial
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.elementsAndClasses.CreateStepper
import com.example.mhealthcat.elementsAndClasses.CreateCommentBox
import com.example.mhealthcat.elementsAndClasses.CreateStarRating
import com.example.mhealthcat.viewModels.SocialViewModel


@Composable
fun Social() {

    val socialViewModel: SocialViewModel = viewModel()
    val showForm by socialViewModel.showForm.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (showForm) {

            CreateSocialForm(
                socialViewModel = socialViewModel
            )
        } else {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                BackgroundAnimation(
                    modifier = Modifier.size(500.dp),
                    videoResId = R.raw.cat_friends
                )
            }
            CreateOutlineButton(
                onClick = { socialViewModel.toggleShowFormOn() },
                buttonText = "Zabeleži novo druženje"
            )

        }
    }


}


// Function screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSocialForm(
    socialViewModel: SocialViewModel
) {
    var showAlert by remember { mutableStateOf(false) }
    val socialForm by socialViewModel.socialForm.collectAsState()

    val peopleList = socialViewModel.peopleList

    val timePickerState = rememberTimePickerState(
        initialHour = socialForm.hours,
        initialMinute = socialForm.minutes,
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        CreateTextBoxNonError(
            modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
            value = socialForm.socialInteraction,
            placeholder = "Vpišite tip druženja",
            label = "Kako ste se družili?",
            onValueChange = { socialViewModel.updateSocialInteraction(it) },
        )


            CreateSelectMenu(
                modifier = Modifier.fillMaxWidth(),
                selectedItem = socialForm.people,
                selectItemsList = peopleList,
                onSelect = { socialViewModel.updatePeople(it) },
                label = "S kom ste se družili?"
            )

            CreateCommentBox (
                value = socialForm.comment,
                label = "Komentar in občutki ob druženju",
                onValueChange = { socialViewModel.updateComment(it) },
                modifier = Modifier.padding(vertical = 15.dp)
            )

        CreateStepper(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth(),
            title = "Število prisotnih:",
            value = socialForm.numberOfPeople,
            valueIncrease = { socialViewModel.increaseNumberOfPeople() },
            valueDecrease = { socialViewModel.decreaseNumberOfPeople() }
        )


        CreateStarRating(
            modifier = Modifier
                .fillMaxWidth(),
            rating = socialForm.rating,
            onRatingChange = { socialViewModel.updateRating(it) },
            starIconModifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Čas druženja:")
        }


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateTimeDial(
                onConfirmButtonClicked = {
                    socialViewModel.updateTime(timePickerState.hour, timePickerState.minute)
                    showAlert = true
                },
                setButtonText = "Zabeleži",
                timePickerState = timePickerState,
                onCancelButtonClicked = { socialViewModel.cancelForm() }
            )
        }
    }
    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                socialViewModel.clearForm()
                showAlert = false
                timePickerState.hour = 0
                timePickerState.minute = 0
            },
            onConfirm = {
                socialViewModel.submitForm()
                showAlert = false
            },
            alertTitle = "Ali želite zabeležiti druženje?",
            alertText = ("Vaš čas druženja je %d ur in %d minut tipa %s s/z %s, " +
                    "ki jih je bilo %d, vaša ocena je bila: %d ⭐ komentar: %s").format(
                socialForm.hours,
                socialForm.minutes,
                socialForm.socialInteraction,
                socialForm.people,
                socialForm.numberOfPeople,
                socialForm.rating,
                socialForm.comment
            )
        )
    }

}


@Preview(showBackground = true)
@Composable
fun SocialPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Social()
        }
    }
}