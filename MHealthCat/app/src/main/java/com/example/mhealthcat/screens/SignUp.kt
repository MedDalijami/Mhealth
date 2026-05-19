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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.padding
import com.example.mhealthcat.R
import com.example.mhealthcat.ElementsAndClasses.AppScreen
import com.example.mhealthcat.ElementsAndClasses.CreateProfileImage
import com.example.mhealthcat.ElementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.ElementsAndClasses.CreateTextField
import com.example.mhealthcat.forms.SignUpForm
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ElementsAndClasses.ShowUserErrorText

@Composable
fun SignUp (onNavigate: (AppScreen) -> Unit){
    var profilePictureUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {selectedUri -> profilePictureUri = selectedUri}
    )

    var form by remember { mutableStateOf(SignUpForm()) }
    var errorPresent by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ProfileImage(profilePictureUri, photoPickerLauncher)

        CreateSignUpForm(
            form = form,
            onFormChange = {form = it}
        )

        CreateOutlineButton(
            onClick = {
                if (form.isValid) {
                    errorPresent = false
                    onNavigate(AppScreen.LogIn)
                } else {
                    errorPresent = true
                }
            },
            buttonText = "Prijava"
        )

        ShowUserErrorText(errorPresent)
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
    form: SignUpForm,
    onFormChange: (SignUpForm) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = form.name,
            onValueChange = { onFormChange(form.copy(name = it)) },
            placeholder = "Ime"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = form.lastName,
            onValueChange = { onFormChange(form.copy(lastName = it)) },
            placeholder = "Priimek"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = form.email,
            onValueChange = { onFormChange(form.copy(email = it)) },
            placeholder = "e-mail",
            isValid = form.isValidEmail,
            errorMsg = "Vnesen e-mail naslov ni veljaven"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = form.password,
            onValueChange = { onFormChange(form.copy(password = it)) },
            placeholder = "Geslo",
            isValid = form.isValidPassword,
            errorMsg = "Geslo mora vsebovati 8 znakov in vsaj eno cifro"
        )
        CreateTextField(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            textFieldValue = form.passwordRepeat,
            onValueChange = { onFormChange(form.copy(passwordRepeat = it)) },
            placeholder = "Prosim ponovite geslo",
            isValid = form.isValidPasswordRepeat,
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
            SignUp { null }
        }
    }
}