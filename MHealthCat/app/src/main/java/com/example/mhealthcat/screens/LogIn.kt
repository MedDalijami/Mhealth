package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.R
import com.example.mhealthcat.elementsAndClasses.AppScreen
import com.example.mhealthcat.elementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.elementsAndClasses.CreateProfileImage
import com.example.mhealthcat.elementsAndClasses.CreateTextField
import com.example.mhealthcat.elementsAndClasses.ShowUserErrorText
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.viewModels.LogInViewModel
import com.example.mhealthcat.viewModels.NavigationViewModel


@Composable
fun LogIn () {
    val navigationViewModel: NavigationViewModel = viewModel()
    val logInViewModel: LogInViewModel = viewModel()

    val showError by logInViewModel.showError.collectAsState()
    val allowSubmit by logInViewModel.allowSubmit.collectAsState()

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

            CreateLoginForm(logInViewModel)
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                CreateOutlineButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (logInViewModel.logIn()){
                            navigationViewModel.changeToScreen(AppScreen.Home)
                        }
                    },
                    buttonText = "Vpiši se",
                    enabled = allowSubmit
                )

                Spacer(modifier = Modifier.width(10.dp))

                CreateOutlineButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationViewModel.changeToScreen(AppScreen.SignUp) },
                    buttonText = "Registriraj se",

                )
            }

            ShowUserErrorText(errorPresent = showError,
                errorText = "Vnesen e-mail ali geslo nista pravilna."
                )
        }
    }

}


@Composable
private fun CreateLoginForm(
    logInViewModel: LogInViewModel
) {
    val logInForm by logInViewModel.logInForm.collectAsState()

    CreateTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        textFieldValue = logInForm.email,
        onValueChange = { logInViewModel.updateEmail(it) },
        isValid = logInViewModel.isValidEmail(),
        placeholder = "e-mail naslov",
        errorMsg = "Vnesen e-mail naslov ni veljaven"
    )
    CreateTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        textFieldValue = logInForm.password,
        onValueChange = { logInViewModel.updatePassword(it) },
        isValid = logInViewModel.isValidPassword(),
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