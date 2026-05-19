package com.example.mhealthcat.ElementsAndClasses

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
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
import com.example.mhealthcat.R
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.RetroPurple
import com.example.mhealthcat.ui.theme.RetroYellow
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
    onConfirmButtonClicked: () -> Unit,
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
            onClick = onConfirmButtonClicked,
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
        onDismissRequest = onDismissRequest,
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
    label: String = "",
    onFocusChange: (FocusState) -> Unit = {}
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
                    .fillMaxWidth()
                    .onFocusChanged { onFocusChange(it) }
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
    onFocusChange: (FocusState) -> Unit = {},
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
        modifier = modifier.onFocusChanged { onFocusChange(it) })
}


@Composable
fun CreateCommentBox(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    minLines: Int = 2,
    maxLines: Int = 8,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        minLines = minLines,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}
@Composable
fun CreateStepper(
    modifier: Modifier = Modifier,
    title: String = "",
    value: Int,
    minValue: Int = 1,
    maxValue: Int = Int.MAX_VALUE,
    step: Int = 1,
    valueIncrease: () -> Unit,
    valueDecrease: () -> Unit
) {

    Column(
        modifier = modifier
    ) {
        if (title.isNotEmpty()){
            Text(text = title)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth()

        ) {
            FilledIconButton(
                onClick = {
                    if (value - step >= minValue){
                        valueDecrease()
                    }
                },
                enabled = value -step >= minValue
            ) {
                Icon(
                    painter = painterResource(R.drawable.remove),
                    contentDescription = "-",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)

                )
            }

            Text(
                text = value.toString(),
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .widthIn(min = 40.dp)
            )

            FilledIconButton(
                onClick = {
                    if (value + step <= maxValue){
                        valueIncrease()
                    }
                },
                enabled = value + step <= maxValue
            ) {

                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "+",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)

                )
            }

        }
    }
}


@Composable
fun CreateStarRating(
    modifier: Modifier = Modifier,
    title: String = "Ocena:",
    rating: Int = 1,
    starRange: IntRange = (1..5),
    starIconModifier: Modifier = Modifier,
    onRatingChange: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {

        if (title.isNotEmpty()) {
            Text(title)
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                starRange.forEach { star ->

                    Icon(
                        painter = painterResource(
                            if (star > rating) R.drawable.star
                            else R.drawable.star_filled
                        ),
                        contentDescription = "Zvezda $star",
                        tint = RetroYellow,
                        modifier = starIconModifier.clickable {
                            onRatingChange(star)
                        }
                    )


                }

            }
        }
    }
}





