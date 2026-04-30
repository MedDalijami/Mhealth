package com.example.mhealthcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.ui.theme.RetroDark
import com.example.mhealthcat.ui.theme.RetroDark2
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.RetroTeal
import com.example.mhealthcat.ui.theme.pixelFont

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MHealthCatTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Screen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


// Functions like pseudo "State machine"
enum class AppScreen {
    Home, LogIn, SignUp, Sleep, Social, Sport, Wellbeing, Settings, User
}

@Composable
fun Screen(modifier: Modifier = Modifier) {
    var currentScreen by remember { mutableStateOf(AppScreen.Home) }

    Box(
        modifier = modifier
            .fillMaxSize()

    )
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(5.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            when (currentScreen)
            {
                AppScreen.Home -> Text("Home")
                AppScreen.LogIn -> Text("Login")
                AppScreen.SignUp -> Text("SignUp")
                AppScreen.Sleep -> Text("Sleep")
                AppScreen.Social -> Text("Social")
                AppScreen.Sport -> Text("Sports")
                AppScreen.Wellbeing -> Text("Wellbeing")
                AppScreen.Settings -> Text("Settings")
                AppScreen.User -> Text("User")

            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(7.dp),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            // Menu is always visible apart from when user is on LogIn/ SignUp
            if (currentScreen != AppScreen.LogIn && currentScreen != AppScreen.SignUp){
                CreateMenuBar()
            }

        }
    }
}

@Composable
private fun CreateMenuBar() {
    var showNavMenu by remember { mutableStateOf(false) }

    var showUserMenu by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f)
        )
        {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clickable { showNavMenu = !showNavMenu },
                shape = CircleShape,

                border = BorderStroke(3.dp, RetroPixelBorder)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    modifier = Modifier.padding(5.dp),
                    contentDescription = "Functions menu",
                    colorFilter = ColorFilter.tint(RetroPixelBorder)
                )
            }
            val functionalityList = listOf(
                Triple("Domov", "\uD83C\uDFE1", AppScreen.Home),
                Triple("Spanje", "\uD83C\uDF19", AppScreen.Sleep),
                Triple("Druženje", "\uD83D\uDC7E", AppScreen.Social),
                Triple("Šport in aktivnost", "\uD83D\uDC5F", AppScreen.Sport),
                Triple("Splošno počutje", "\uD83E\uDEC2", AppScreen.Wellbeing)
            )
            MakeDropDownMenu(
                expended = showNavMenu,
                onDismissRequest = { showNavMenu = false },
                items = functionalityList
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.6f)
        ) {
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clickable { showUserMenu = !showUserMenu },
                shape = CircleShape,
                border = BorderStroke(3.dp, RetroPixelBorder)

            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_menu),
                    modifier = Modifier.padding(5.dp),
                    contentDescription = "Urer menu",
                    colorFilter = ColorFilter.tint(RetroPixelBorder)
                )
            }

            val userMenuList = listOf(
                Triple("Uporabnik", "\uD83D\uDC64", AppScreen.User),
                Triple("Nastavitve", "⚙\uFE0F", AppScreen.Settings)
            )

            MakeDropDownMenu(
                expended = showUserMenu,
                onDismissRequest = {showUserMenu = false},
                items = userMenuList
            )

        }
    }

}


@Composable
fun MakeDropDownMenu(
    expended: Boolean,
    onDismissRequest: () -> Unit,
    items: List<Triple<String, String, AppScreen>>,
    onItemClick: () -> Unit = onDismissRequest,
) {
    DropdownMenu(
        expanded = expended,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .background(RetroDark)
            .border(
                width = 3.dp,
                color = RetroPixelBorder,
                shape = RectangleShape
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 150,
                    easing = LinearEasing
                )
            )
    ) {

        items.forEachIndexed { index, (label, emoji, state) ->
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = emoji,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = label,
                            fontFamily = pixelFont,
                            fontSize = 9.sp,
                            color = RetroTeal,
                            letterSpacing = 1.sp
                        )
                    }
                },
                onClick = onItemClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (index % 2 == 0) {
                            RetroDark
                        } else {
                            RetroDark2
                        }
                    )
            )
            if (index != items.lastIndex) {
                HorizontalDivider(
                    color = RetroPixelBorder.copy(alpha = 0.4f),
                    thickness = 1.dp
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MHealthCatTheme {
        Screen()
    }
}

