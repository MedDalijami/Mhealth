package com.example.mhealthcat.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mhealthcat.AppScreen
import com.example.mhealthcat.CreateImage
import com.example.mhealthcat.R
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.roboto


@Composable
fun Login (onNavigate: (AppScreen) -> Unit) {
    var password by remember { mutableStateOf("") }
    val isValidPassword = password.length >= 8 && password.any {it.isDigit()}
    var email by remember { mutableStateOf("") }
    val isValidEmail = email.contains("@")
    var errorMsg by remember {mutableStateOf(false)}

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CreateImage(
            imgRes = R.drawable.user_menu,
            description = "Profile picture",
            color = Color.White,
            modifier = Modifier
                .fillMaxSize(0.35f)
                .aspectRatio(1f)
        )

        Column {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        text = "e-mail naslov",
                        fontFamily = roboto,
                        fontSize = 20.sp
                    )
                },
                singleLine = true,
                isError = email.isNotEmpty() && !isValidEmail,
                supportingText = {
                    if (email.isNotEmpty() && !isValidEmail) {
                        Text(
                            text = "e-mail mora vsebovati @",
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
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text(
                        text = "geslo",
                        fontFamily = roboto,
                        fontSize = 20.sp
                    )
                },
                singleLine = true,
                isError = password.isNotEmpty() && !isValidPassword,
                supportingText = {
                    if (password.isNotEmpty() && !isValidPassword) {
                        Text(
                            text = "Geslo mora vsebovati 8 znakov in vsaj eno cifro",
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
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                OutlinedButton(
                    modifier = Modifier.weight(0.45f),
                    onClick = {
                        if (isValidEmail && isValidPassword) {
                            onNavigate(AppScreen.Home)
                            errorMsg = false
                        }
                        else {
                            errorMsg = true
                        }
                    },
                    border = BorderStroke(width = 3.dp, color = RetroPixelBorder)
                ) {
                    Text(
                        text = "Vpiši se",
                        fontSize = 20.sp,
                        fontFamily = roboto,
                        color = Color.White
                    )
                }
                Card(
                    modifier = Modifier
                        .weight(0.1f)
                ) { }

                OutlinedButton(
                    modifier = Modifier.weight(0.45f),
                    onClick = {
                        onNavigate(AppScreen.SignUp)
                    },
                    border = BorderStroke(width = 3.dp, color = RetroPixelBorder)
                ) {
                    Text(
                        text = "Registriraj se",
                        fontSize = 20.sp,
                        fontFamily = roboto,
                        color = Color.White
                    )
                }
            }
            if (errorMsg) {
                Text(
                    text = "Prosim vpišite pravilne podatke",
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
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Login(
                onNavigate = { state ->
                    Log.d("Navigation","Next screen $state")
                })
        }
    }
}