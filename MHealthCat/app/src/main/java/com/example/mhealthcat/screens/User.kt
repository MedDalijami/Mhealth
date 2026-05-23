package com.example.mhealthcat.screens


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.ElementsAndClasses.AppScreen
import com.example.mhealthcat.ElementsAndClasses.CreateAlert
import com.example.mhealthcat.ElementsAndClasses.CreateOutlineButton
import com.example.mhealthcat.ElementsAndClasses.CreateProfileImage
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ElementsAndClasses.CreateTextField
import com.example.mhealthcat.ElementsAndClasses.ProfileImage
import com.example.mhealthcat.R
import com.example.mhealthcat.ui.theme.RetroRed
import com.example.mhealthcat.viewModels.NavigationViewModel
import com.example.mhealthcat.viewModels.UserViewModel

@Composable
fun User() {
    val navigationViewModel: NavigationViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()



    Column(
        modifier = Modifier
            .padding(top = 60.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (userViewModel.editingPassword.collectAsState().value) {
            EditPassword(userViewModel)

        } else{
            if (userViewModel.editingProfile.collectAsState().value) {
                CreateUserInfo(
                    userViewModel = userViewModel,
                    navigationViewModel = navigationViewModel,
                    editing = true
                )
            }
            else {
                CreateUserInfo(
                    userViewModel = userViewModel,
                    navigationViewModel = navigationViewModel,
                )
            }

        }

    }



}

@Composable
fun CreateUserInfo(
    userViewModel: UserViewModel,
    navigationViewModel: NavigationViewModel,
    editing: Boolean = false
)
{

    val userProfile by userViewModel.userProfile.collectAsState()


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { selectedUri -> userViewModel.updateProfilePicture(selectedUri) }
    )


    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        ProfileImage(
            profilePictureUri = userProfile.profilePictureUri,
            photoPickerLauncher = photoPickerLauncher,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary),
            buttonVisible = editing

        )

        Column(
            modifier = Modifier.padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = userProfile.name,
                onValueChange = { userViewModel.updateName(it) },
                placeholder = "Ime",
                enabled = editing
            )
            CreateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = userProfile.lastName,
                onValueChange = { userViewModel.updateLastName(it) },
                placeholder = "Priimek",
                enabled = editing
            )
            CreateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = userProfile.email,
                onValueChange = {
                    userViewModel.updateEmail(it)
                },
                placeholder = "e-mail",
                isValid = userViewModel.isValidEmail(),
                errorMsg = "Vnesen e-mail naslov ni veljaven",
                enabled = editing
            )
            Spacer(modifier = Modifier.weight(1f))
            if (!editing)
            CreateUserProfileButtonsView(
                userViewModel = userViewModel,
                navigationViewModel = navigationViewModel
            ) else CreateUserProfileButtonsEditProfile(
                userViewModel = userViewModel
            )
        }


    }
}

@Composable
fun EditPassword(userViewModel: UserViewModel) {

    val userProfile by userViewModel.userProfile.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateProfileImage(
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 25.dp)
                .aspectRatio(1f),
            color = Color.White,
            imgRes = R.drawable.user_menu,
            imgUri = userProfile.profilePictureUri,
            description = "Profile picture",

        )

        Column(
            modifier = Modifier.padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CreateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = userProfile.password,
                onValueChange = { userViewModel.updateCurrentPassword(it) },
                placeholder = "Vaše trenutno geslo",
                isValid = userViewModel.isValidPassword(),
                errorMsg = "Geslo mora vsebovati 8 znakov in vsaj eno cifro"
            )
            CreateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = userViewModel.newPassword.collectAsState().value,
                onValueChange = { userViewModel.updateNewPassword(it) },
                placeholder = "Vaše novo geslo",
                isValid = userViewModel.isValidNewPassword(),
                errorMsg = "Geslo mora vsebovati 8 znakov in vsaj eno cifro"
            )

            CreateTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textFieldValue = userViewModel.newPasswordRepeat.collectAsState().value,
                onValueChange = { userViewModel.updateNewPasswordRepeat(it) },
                placeholder = "Ponovite novo geslo",
                isValid = userViewModel.isValidPasswordRepeat(),
                errorMsg = "Vnešeni gesli se ne ujemata"
            )

            Spacer(modifier = Modifier.weight(1f))
            CreateUserProfileButtonsPassword(
                userViewModel = userViewModel
            )
        }


    }

}


@Composable
fun CreateUserProfileButtonsView (
    userViewModel: UserViewModel,
    navigationViewModel: NavigationViewModel
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CreateOutlineButton(
            modifier = Modifier.weight(1f),
            onClick = { userViewModel.toggleEditingProfileOn()},
            buttonText = "Uredi profil",
            fontSize = 18.sp,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(10.dp))

        CreateOutlineButton(
            modifier = Modifier.weight(1f),
            onClick = { userViewModel.toggleEditingPasswordOn() },
            buttonText = "Spremeni geslo",
            fontSize = 18.sp,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        )
    }
    CreateOutlineButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding( start = 20.dp, end = 20.dp, top = 10.dp),
        onClick = {
            userViewModel.clearUserProfile()
            navigationViewModel.changeToScreen(AppScreen.LogIn)
        },
        buttonText = "Izpis iz profila",
        fontSize = 18.sp,
        border = BorderStroke(width = 3.dp, color = RetroRed)
    )
}


@Composable
fun CreateUserProfileButtonsEditProfile (
    userViewModel: UserViewModel
) {

    var showAlert by remember {mutableStateOf(false)}

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CreateOutlineButton(
            modifier = Modifier.weight(1f),
            onClick = {
                userViewModel.resetUserProfile()
                userViewModel.toggleEditingProfileOff()
            },
            buttonText = "Prekliči",
            fontSize = 18.sp,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(10.dp))

        CreateOutlineButton(
            modifier = Modifier.weight(1f),
            onClick = {
                showAlert = true
            },
            buttonText = "Shrani",
            fontSize = 18.sp,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary),
        )
    }

    if (showAlert) {
        CreateAlert(
            alertTitle = if (userViewModel.isValidForEditProfile()) "" +
                    "Želite shraniti spremembe?" else "Vnešeni podatki so napačni",
            alertText = if (userViewModel.isValidForEditProfile()) "" +
                    "Prosimo vas da potrdite spremembe" else "Prosim vnesite pravilne podatke, polja ime in priimek ne smeta biti prazni " +
                    "in e-main mora biti veljaven",
            onDismissRequest = {
                showAlert = false
            },
            onConfirm = {
                if (userViewModel.isValidForEditProfile()) {
                    userViewModel.saveUserProfile()
                }
                showAlert = false
                userViewModel.toggleEditingProfileOff()
            }
        )
    }
}

@Composable
fun CreateUserProfileButtonsPassword (
    userViewModel: UserViewModel
) {

    var showAlert by remember {mutableStateOf(false)}
    val allowPasswordSubmit by userViewModel.allowEditPasswordSubmit.collectAsState()

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CreateOutlineButton(
            modifier = Modifier.weight(1f),
            onClick = {
                userViewModel.toggleEditingPasswordOff()
            },
            buttonText = "Prekliči",
            fontSize = 18.sp,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary)
        )

        Spacer(modifier = Modifier.width(10.dp))

        CreateOutlineButton(
            modifier = Modifier.weight(1f),
            onClick = {
                showAlert = true
            },
            buttonText = "Shrani",
            fontSize = 18.sp,
            border = BorderStroke(width = 3.dp, color = MaterialTheme.colorScheme.primary),
            enabled = allowPasswordSubmit
        )
    }

    if (showAlert) {
        CreateAlert(
            alertTitle = if (userViewModel.isPasswordCorrect()) "" +
                    "Želite shraniti spremembe?" else "Vpisali ste napačno geslo",
            alertText = if (userViewModel.isPasswordCorrect()) "" +
                    "Prosimo vas da potrdite novo geslo" else "Prosim vnesite pravilno geslo",
            onDismissRequest = {
                showAlert = false
            },
            onConfirm = {
                if (userViewModel.isPasswordCorrect()) {
                    userViewModel.saveNewPassword()
                }
                showAlert = false
                userViewModel.toggleEditingPasswordOff()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            User()
        }
    }
}