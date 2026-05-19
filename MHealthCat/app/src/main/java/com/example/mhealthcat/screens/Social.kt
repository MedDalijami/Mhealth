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
import androidx.compose.material3.Text
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
import com.example.mhealthcat.ElementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.ElementsAndClasses.CreateSelectMenu
import com.example.mhealthcat.ElementsAndClasses.CreateTextBoxNonError
import com.example.mhealthcat.ElementsAndClasses.CreateTimeDial
import com.example.mhealthcat.forms.SocialForm
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ElementsAndClasses.CreateStepper
import com.example.mhealthcat.ElementsAndClasses.CreateCommentBox
import com.example.mhealthcat.ElementsAndClasses.CreateStarRating



@Composable
fun Social() {

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
                buttonText = "Zabeleži novo druženje"
            )
        } else {
            CreateSocialForm(
                onSubmit = { showForm = false }
            )
        }
    }


}


// Function screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSocialForm(
    onSubmit: () -> Unit
) {
    var showAlert by remember { mutableStateOf(false) }
    var socialForm by remember { mutableStateOf(SocialForm()) }

    val socialInteractionTypeList = listOf("Osebno", "Klic", "Skupinsko", "Drugo")
    val peopleList = listOf("Prijatelji", "Partner/ka" ,"Družina", "Neznanci")

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
        CreateSelectMenu(
            modifier = Modifier.fillMaxWidth(),
            selectedItem = socialForm.socialInteraction,
            selectItemsList = socialInteractionTypeList,
            onSelect = { socialForm = socialForm.copy(socialInteraction = it) },
            label = "Tip druženja"
        )

        if (socialForm.isOther) {
            CreateTextBoxNonError(
                modifier = Modifier.fillMaxWidth(),
                value = socialForm.socialInteractionOther,
                placeholder = "Vpišite tip druženja",
                onValueChange = { socialForm = socialForm.copy(socialInteractionOther = it) },
            )
        }

        CreateSelectMenu(
            modifier = Modifier.fillMaxWidth(),
            selectedItem = socialForm.people,
            selectItemsList = peopleList,
            onSelect = { socialForm = socialForm.copy(people = it) },
            label = "S kom ste se družili?"
        )

        CreateCommentBox(
            value = socialForm.comment,
            label = "Komentar in občutki ob druženju",
            onValueChange = { socialForm = socialForm.copy(comment = it) }
        )

        CreateStepper(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .fillMaxWidth(),
            title = "Število prisotnih:",
            value = socialForm.numberOfPeople,
            valueIncrease = { socialForm = socialForm.increaseNumberOfPeople() },
            valueDecrease = { socialForm = socialForm.decreaseNumberOfPeople() }
        )


        CreateStarRating(
            modifier = Modifier
                .fillMaxWidth(),
            rating = socialForm.rating,
            onRatingChange = {socialForm = socialForm.copy(rating = it)},
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
                onClick = {
                    socialForm = socialForm.copy(
                        hours = timePickerState.hour,
                        minutes = timePickerState.minute
                    )
                    showAlert = true },
                setButtonText = "Zabeleži druženje",
                timePickerState = timePickerState
            )
        }
    }
    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                socialForm = SocialForm()
                showAlert = false
                timePickerState.hour = 0
                timePickerState.minute = 0
            },
            onConfirm = {
                onSubmit()
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