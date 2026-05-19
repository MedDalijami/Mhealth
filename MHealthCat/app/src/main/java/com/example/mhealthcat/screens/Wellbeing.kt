package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.mhealthcat.forms.WellbeingForm
import com.example.mhealthcat.ui.theme.MHealthCatTheme

@Composable
fun Wellbeing() {
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
                buttonText = "Zabeleži svoje dnevno počutje"
            )
        } else {
            // form
            CreateWellbeingForm (onConfirm = {showForm = false})
        }
    }
}

@Composable
fun CreateWellbeingForm (onConfirm: () -> Unit) {
    var wellbeingForm by remember { mutableStateOf(WellbeingForm()) }
    var showAlert by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        CreateStarRating(
            modifier = Modifier.padding(bottom = 15.dp),
            title = "Ocenite svoje počutje danes:",
            rating = wellbeingForm.rating,
            onRatingChange = {wellbeingForm = wellbeingForm.copy(rating = it)},
            starIconModifier = Modifier.size(40.dp)
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = wellbeingForm.generalFeelings,
            label = "Opišite svoje splošno počutje danes",
            minLines = 1,
            maxLines = 2,
            onValueChange = {wellbeingForm = wellbeingForm.copy(generalFeelings = it)}
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = wellbeingForm.generalFears,
            label = "Opišite kaj vam je danes povzročalo skrb",
            minLines = 1,
            maxLines = 2,
            onValueChange = {wellbeingForm = wellbeingForm.copy(generalFears = it)}
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = wellbeingForm.somethingGoodThatHappened,
            label = "Opišite kaj vam je bilo danes najbolj všeč",
            onValueChange = {wellbeingForm = wellbeingForm.copy(somethingGoodThatHappened = it)}
        )

        CreateOutlineButton(
            onClick = {showAlert = true},
            buttonText = "Zabeleži današnje počutje"
        )
    }

    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                wellbeingForm = WellbeingForm()
                showAlert = false
            },
            onConfirm = {
                onConfirm()
                showAlert = false
            },
            alertTitle = "Ali želite zabeležiti današnje počutje?",
            alertText = ("Svoje počutje ste danes ocenili z %d ⭐" +
                    "počutje ste opisali kot: %s, največ skrbi vam povzroča: %s" +
                    "Najbolj vam je bilo všeč danes: %s").format(
                    wellbeingForm.rating,
                        wellbeingForm.generalFeelings,
                        wellbeingForm.generalFears,
                        wellbeingForm.somethingGoodThatHappened
            )
        )
    }

}

@Preview(showBackground = true)
@Composable
fun WellbeingPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Wellbeing()
        }
    }
}