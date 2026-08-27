package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.mhealthcat.elementsAndClasses.CreateCommentBox
import com.example.mhealthcat.elementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.elementsAndClasses.CreateStarRating
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.viewModels.WellbeingViewModel

@Composable
fun Wellbeing() {

    val wellbeingViewModel : WellbeingViewModel = viewModel()

    val showForm by wellbeingViewModel.showForm.collectAsState()

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
                    videoResId = R.raw.general_feelings
                )
            }

            CreateOutlineButton(
                onClick = {
                    wellbeingViewModel.toggleShowFormOn()
                },
                buttonText = "Zabeleži svoje dnevno počutje"
            )
        } else {
            // form
            CreateWellbeingForm (wellbeingViewModel)
        }
    }
}

@Composable
fun CreateWellbeingForm (
    wellbeingViewModel: WellbeingViewModel
) {
    val wellbeingForm by wellbeingViewModel.wellbeingForm.collectAsState()
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
            onRatingChange = { wellbeingViewModel.updateRating(it) },
            starIconModifier = Modifier.size(40.dp)
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = wellbeingForm.generalFeelings,
            label = "Opišite svoje splošno počutje danes",
            minLines = 1,
            maxLines = 2,
            onValueChange = { wellbeingViewModel.updateGeneralFeelings(it) }
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = wellbeingForm.generalFears,
            label = "Opišite kaj vam je danes povzročalo skrb",
            minLines = 1,
            maxLines = 2,
            onValueChange = { wellbeingViewModel.updateGeneralFears(it) }
        )

        CreateCommentBox(
            modifier = Modifier.padding(bottom = 15.dp),
            value = wellbeingForm.somethingGoodThatHappened,
            label = "Opišite kaj vam je bilo danes najbolj všeč",
            onValueChange = { wellbeingViewModel.updateSomethingGoodThatHappened(it) }
        )

        CreateOutlineButton(
            onClick = {showAlert = true},
            buttonText = "Zabeleži današnje počutje"
        )
    }

    if (showAlert) {
        CreateAlert(
            onDismissRequest = {
                wellbeingViewModel.clearForm()
                showAlert = false
            },
            onConfirm = {
                wellbeingViewModel.submitForm()
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