package com.example.mhealthcat.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.R
import com.example.mhealthcat.ElementsAndClasses.AppScreen
import com.example.mhealthcat.ElementsAndClasses.CreateProfileImage
import com.example.mhealthcat.ElementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.ElementsAndClasses.CreateTextField
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ElementsAndClasses.ShowUserErrorText
import com.example.mhealthcat.viewModels.NavigationViewModel
import com.example.mhealthcat.viewModels.SignUpViewModel

@Composable
fun SignUp (){
    val navigationViewModel : NavigationViewModel = viewModel()
    val signUpViewModel : SignUpViewModel = viewModel()


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {selectedUri -> signUpViewModel.updateProfilePicture(selectedUri)}
    )

    val profilePictureUri: Uri? = signUpViewModel.signUpForm.collectAsState().value.profilePictureUri

    val showError by signUpViewModel.showError.collectAsState()
    val allowSubmit by signUpViewModel.allowSubmit.collectAsState()



    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileImage(profilePictureUri, photoPickerLauncher)

        CreateSignUpForm(
            signUpViewModel
        )

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
                        if (signUpViewModel.signUp()) {
                            navigationViewModel.changeToScreen(AppScreen.LogIn)
                        }
                    },
                    buttonText = "Registriraj se",
                    enabled = allowSubmit
                )

                Spacer(modifier = Modifier.width(10.dp))

                CreateOutlineButton(
                    modifier = Modifier.weight(1f),
                    onClick = { navigationViewModel.changeToScreen(AppScreen.LogIn) },
                    buttonText = "Nazaj na vpis"
                )
            }
        }

        ShowUserErrorText(
            errorPresent = showError,
            errorText = "Vnesen e-mail naslov že obstaja. Prosimo uporabite drug e-mail naslov.")
    }


}

@Composable
private fun ProfileImage(
    profilePictureUri: Uri?,
    photoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>
) {
    Column(
        modifier = Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateProfileImage(
            modifier = Modifier
                .size(160.dp)
                .aspectRatio(1f),
            color = Color.White,
            imgRes = if (profilePictureUri == null) R.drawable.user_menu else null,
            imgUri = profilePictureUri,
            description = "Profile picture"
        )
        CreateOutlineButton(
            onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            buttonText = "Izberi profilno sliko"
        )
    }
}


@Composable
fun CreateSignUpForm(
    signUpViewModel: SignUpViewModel
) {
    val signUpForm by signUpViewModel.signUpForm.collectAsState()
    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = signUpForm.name,
            onValueChange = { signUpViewModel.updateName(it) },
            placeholder = "Ime"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = signUpForm.lastName,
            onValueChange = { signUpViewModel.updateLastName(it) },
            placeholder = "Priimek"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = signUpForm.email,
            onValueChange = { signUpViewModel.updateEmail(it) },
            placeholder = "e-mail",
            isValid = signUpViewModel.isValidEmail(),
            errorMsg = "Vnesen e-mail naslov ni veljaven"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = signUpForm.password,
            onValueChange = { signUpViewModel.updatePassword(it) },
            placeholder = "Geslo",
            isValid = signUpViewModel.isValidPassword(),
            errorMsg = "Geslo mora vsebovati 8 znakov in vsaj eno cifro"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = signUpForm.passwordRepeat,
            onValueChange = { signUpViewModel.updatePasswordRepeat(it) },
            placeholder = "Prosim ponovite geslo",
            isValid = signUpViewModel.isValidPasswordRepeat(),
            errorMsg = "Vnešeni gesli se ne ujemata"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SignUp()
        }
    }
}