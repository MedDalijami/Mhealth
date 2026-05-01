package com.example.mhealthcat.functionsAndLibraries

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.mhealthcat.ui.theme.roboto
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



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








// FUNCTIONS

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}












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
