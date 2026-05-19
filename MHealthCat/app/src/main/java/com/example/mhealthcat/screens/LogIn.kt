package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.mhealthcat.forms.LogInForm
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.R
import com.example.mhealthcat.ElementsAndClasses.AppScreen
import com.example.mhealthcat.ElementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.ElementsAndClasses.CreateProfileImage
import com.example.mhealthcat.ElementsAndClasses.CreateTextField
import com.example.mhealthcat.ElementsAndClasses.ShowUserErrorText
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.viewModels.NavigationViewModel


@Composable
fun LogIn () {
    val navigationViewModel: NavigationViewModel = viewModel()
    var form by remember { mutableStateOf(LogInForm()) }
    var errorPresent by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateProfileImage(
            modifier = Modifier
                .size(160.dp)
                .aspectRatio(1f),
            color = Color.White,
            imgRes = R.drawable.user_menu,
            description = "Profile picture"
        )

        Column {

            CreateLoginForm(
                form = form,
                onFormChange = {form = it})
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                CreateOutlineButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (form.isValid) {
                            navigationViewModel.changeToScreen(AppScreen.Home)
                            errorPresent = false
                        }
                        else {
                            errorPresent = true
                        }
                    },
                    buttonText = "Vpiši se"
                )
                Card(
                    modifier = Modifier
                        .weight(0.1f)
                ) { }

                CreateOutlineButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationViewModel.changeToScreen(AppScreen.SignUp) },
                    buttonText = "Registriraj se"
                )
            }

            ShowUserErrorText(errorPresent)
        }
    }

}


@Composable
private fun CreateLoginForm(
    form: LogInForm,
    onFormChange: (LogInForm) -> Unit
) {
    CreateTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        textFieldValue = form.email,
        onValueChange = { onFormChange(form.copy(email = it)) },
        isValid = form.isValidEmail,
        placeholder = "e-mail naslov",
        errorMsg = "Vnesen e-mail naslov ni veljaven"
    )
    CreateTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        textFieldValue = form.password,
        onValueChange = { onFormChange(form.copy(password = it)) },
        isValid = form.isValidPassword,
        placeholder = "geslo",
        errorMsg = "Geslo mora vsebovati 8 znakov in vsaj eno cifro"
    )
}
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            LogIn()
        }
    }
}