package com.example.mhealthcat.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.mhealthcat.functionsAndLibraries.CreateAlert
import com.example.mhealthcat.functionsAndLibraries.CreateOutlineButton
import com.example.mhealthcat.functionsAndLibraries.CreateSelectMenu
import com.example.mhealthcat.functionsAndLibraries.CreateTextBoxNonError
import com.example.mhealthcat.functionsAndLibraries.CreateTimeDial
import com.example.mhealthcat.functionsAndLibraries.SocialFormState
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.functionsAndLibraries.CreateStepper
import com.example.mhealthcat.functionsAndLibraries.CreateCommentBox

@Composable
fun Social() {

    var showForm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {


        if (!showForm)
        CreateOutlineButton(
            onClick = {showForm = true},
            buttonText = "Zabeleži novo druženje"
        )
        else {
            CreateSocialForm(
                onSubmit = {showForm = false}
            )
        }
    }



}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSocialForm (
    onSubmit: () -> Unit
) {
    var showAlert by remember { mutableStateOf(false) }
    var formState by remember { mutableStateOf(SocialFormState()) }

    val socialInteractionTypeList = listOf("Osebno", "Klic", "Skupinsko", "Drugo")
    val peopleList = listOf("Prijatelji", "Družina", "Neznanci")

    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0,
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
            selectedItem = formState.socialInteraction,
            selectItemsList = socialInteractionTypeList,
            onSelect = { formState = formState.copy(socialInteraction = it) },
            label = "Tip druženja"
        )

        if (formState.isOther) {
            CreateTextBoxNonError(
                modifier = Modifier.fillMaxWidth(),
                value = formState.socialInteractionOther,
                placeholder = "Vpišite tip druženja",
                onValueChange = { formState = formState.copy(socialInteractionOther = it) },
            )
        }

        CreateSelectMenu(
            modifier = Modifier.fillMaxWidth(),
            selectedItem = formState.people,
            selectItemsList = peopleList,
            onSelect = { formState = formState.copy(people = it) },
            label = "S kom ste se družili?"
        )

        CreateStepper(
            modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth(),
            label = "Število prisotnih:",
            value = formState.numberOfPeople,
            valueIncrease = { formState = formState.increaseNumberOfPeople() },
            valueDecrease =  {formState = formState.decreaseNumberOfPeople()}
        )

        CreateCommentBox(
            value = formState.comment,
            label = "Komentar in občutki ob druženju",
            onValueChange = {formState = formState.copy(comment = it)}
        )

        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Čas druženja:")
        }




        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateTimeDial(
                onClick = { showAlert = true },
                setButtonText = "Zabeleži druženje",
                timePickerState = timePickerState
            )
        }
    }
    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                formState = SocialFormState()
                showAlert = false
            },
            onConfirm = {
                onSubmit()
                showAlert = false
            },
            alertTitle = "Ali želite zabeležiti druženje?",
            alertText = "Vaš čas druženja je %d ur in %d minut tipa %s s/z %s, ki jih je bilo %d komentar: %s".format(
                timePickerState.hour,
                timePickerState.minute,
                formState.socialInteraction,
                formState.people,
                formState.numberOfPeople,
                formState.comment
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