package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.sp
import com.example.mhealthcat.functionsAndLibraries.CreateAlert
import com.example.mhealthcat.functionsAndLibraries.CreateSelectMenu
import com.example.mhealthcat.functionsAndLibraries.CreateTextBoxNonError
import com.example.mhealthcat.functionsAndLibraries.CreateTimeDial
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ui.theme.roboto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Social() {
    var showTime by remember { mutableStateOf(false) }

    var socialInteraction by remember { mutableStateOf("Osebno") }
    val socialInteractionTypeList = listOf("Osebno", "Klic", "Skupinsko","Drugo")
    var socialInteractionOther by remember { mutableStateOf("") }

    var people by remember { mutableStateOf("Prijatelji") }
    val peopleList = listOf("Prijatelji", "Družina", "Neznanci")

    val timePickerState = rememberTimePickerState(
        initialHour = 0,
        initialMinute = 0,
        is24Hour = true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 30.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CreateSelectMenu(
                selectedItem = socialInteraction,
                selectItemsList = socialInteractionTypeList,
                onSelect = { socialInteraction = it },
                label = "Tip druženja"
            )

            if (socialInteraction.equals("Drugo")) {
                CreateTextBoxNonError(
                    value = socialInteractionOther,
                    placeholder = "Vpišite tip druženja",
                    onValueChange = {socialInteractionOther = it}
                )
            }

            CreateSelectMenu(
                selectedItem = people,
                selectItemsList = peopleList,
                onSelect = { people = it },
                label = "S kom ste se družili?"
            )




        }

        CreateTimeDial(
            onClick = {showTime = true},
            setButtonText = "Zabeleži druženje",
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
            alertTitle = "Ali želite zabeležiti druženje?",
            alertText = "Vaš čas druženja je %d ur in %d minut".format(
                timePickerState.hour,
                timePickerState.minute
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