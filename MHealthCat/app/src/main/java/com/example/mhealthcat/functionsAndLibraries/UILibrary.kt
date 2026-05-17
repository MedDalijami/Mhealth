package com.example.mhealthcat.functionsAndLibraries

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.RetroPurple
import com.example.mhealthcat.ui.theme.roboto


// COMPOSABLES
@Composable
fun CreateProfileImage (
    modifier: Modifier = Modifier,
    color: Color = RetroPixelBorder,
    imgRes: Int? = null,
    imgUri: Uri? = null,
    description: String,


    ) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(3.dp, color)

    ) {
        if (imgRes != null) {
            Image(
                modifier = Modifier.padding(5.dp),
                painter = painterResource(id = imgRes),
                contentDescription = description,
                colorFilter = ColorFilter.tint(color)
            )
        }
        if(imgUri != null){
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = imgUri,
                contentScale = ContentScale.Crop,
                contentDescription = description
            )
        }
    }
}

@Composable
fun CreateOutlineButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    border: BorderStroke = BorderStroke(width = 3.dp, color = RetroPixelBorder),
    buttonText: String,
    fontFamily: FontFamily = roboto,
    fontSize: TextUnit = 20.sp
){
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = border
    ) {
        Text(
            text = buttonText,
            fontSize = fontSize,
            fontFamily = fontFamily,
            color = Color.White
        )
    }
}
@Composable
fun CreateTextField (
    modifier: Modifier = Modifier,
    textFieldValue: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean = true,
    placeholder: String = textFieldValue,
    errorMsg: String = "Prišlo je do napake"
) {
    OutlinedTextField(
        value = textFieldValue,
        modifier = modifier,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = roboto,
                fontSize = 20.sp
            )
        },
        singleLine = true,
        isError = !isValid && textFieldValue.isNotEmpty(),
        supportingText = {
            if (!isValid && textFieldValue.isNotEmpty()) {
                Text(
                    text = errorMsg,
                    fontFamily = roboto,
                    fontSize = 15.sp,
                    color = Color.Red
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            errorBorderColor = Color.Red,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = Color.White
        ),
        textStyle = TextStyle(
            fontFamily = roboto,
            fontSize = 20.sp
        )
    )
}



@Composable
fun ShowUserErrorText(
    errorPresent: Boolean,
    errorText: String = "Prosim vpišite pravilne podatke"
) {
    if (errorPresent) {
        Text(
            text = errorText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            fontSize = 15.sp,
            fontFamily = roboto,
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTimeDial (
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    setButtonText: String,
    timePickerState: TimePickerState
) {

    Column(
        modifier = modifier

    ) {
        TimeInput(
            state = timePickerState,
            colors = TimePickerDefaults.colors(
                timeSelectorSelectedContainerColor = RetroPurple,
                timeSelectorUnselectedContainerColor = Color.Transparent,
                timeSelectorSelectedContentColor = Color.White,
                timeSelectorUnselectedContentColor = Color.White.copy(0.5f),
                containerColor = Color.Transparent
            )
        )

        CreateOutlineButton(
            onClick = onClick,
            buttonText = setButtonText
        )

    }
}


@Composable
fun CreateAlert (
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit = onDismissRequest,
    alertTitle: String,
    alertText: String,
){
    AlertDialog(
        title = {
            Text(alertTitle)
        },
        text = {
            Text(alertText)
        },
        onDismissRequest = {onDismissRequest},
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Potrdi")
            }
        },
        dismissButton  = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text("Zapri")
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSelectMenu (
    modifier: Modifier = Modifier,
    selectedItem: String,
    selectItemsList: List<String>,
    onSelect: (String) -> Unit,
    label: String = ""
) {
    var expended by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        ExposedDropdownMenuBox(
            expanded = expended,
            // this checks if user clicked outside select and if so sets the value to false
            onExpandedChange = { expended = it },
        ) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = {},
                readOnly = true,
                label = { Text(label) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon( expanded = expended )
                },
                modifier = Modifier
                    .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryEditable, enabled = true)
            )

            ExposedDropdownMenu(
                expanded = expended,
                onDismissRequest = {expended = false}
            ) {
                selectItemsList.forEach { selectItem ->
                    DropdownMenuItem(
                        text = { Text(selectItem) },
                        onClick = {onSelect(selectItem)
                            expended = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }

        }
    }
}

@Composable
fun CreateTextBoxNonError (
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onValueChange: (String) -> Unit,
    label: String = ""
) {

    OutlinedTextField(
        value = value,
        placeholder = {
            Text(
                text = placeholder
            )
        },
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        modifier = modifier)
}







// FUNCTIONS


// CLASSES
data class SignUpFormState(
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = ""
) {
    val isValidEmail get() = email.contains("@") && email.contains(".")
    val isValidPassword get() = password.length >= 8 && password.any { it.isDigit() }
    val isValidPasswordRepeat get() = passwordRepeat == password && isValidPassword
    val isValid get() = name.isNotEmpty() && lastName.isNotEmpty()
            && isValidEmail && isValidPassword && isValidPasswordRepeat
}

data class LogInFormState(
    val email:  String = "",
    val password: String = ""
) {
    val isValidEmail get() = email.contains("@") && email.contains(".")
    val isValidPassword get() = password.length >= 8 && password.any { it.isDigit() }
    val isValid get() = isValidEmail && isValidPassword
}

enum class AppScreen {
    Home, LogIn, SignUp, Sleep, Social, Sport, Wellbeing, Settings, User
}
