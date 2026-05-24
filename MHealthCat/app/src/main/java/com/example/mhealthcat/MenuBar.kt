package com.example.mhealthcat

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.ElementsAndClasses.AppScreen
import com.example.mhealthcat.ElementsAndClasses.CreateProfileImage
import com.example.mhealthcat.ui.theme.RetroDark
import com.example.mhealthcat.ui.theme.RetroDark2
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import com.example.mhealthcat.ui.theme.RetroTeal
import com.example.mhealthcat.ui.theme.roboto
import com.example.mhealthcat.viewModels.NavigationViewModel

@Composable
fun MenuBar() {
    val navigationViewModel: NavigationViewModel = viewModel()


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
            CreateProfileImage(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clickable { showNavMenu = !showNavMenu },
                imgRes = R.drawable.baseline_menu_24,
                description = "Functions Menu"
            )
            val functionalityList = listOf(
                Triple("Domov", "\uD83C\uDFE1", AppScreen.Home),
                Triple("Spanje", "\uD83C\uDF19", AppScreen.Sleep),
                Triple("Druženje", "\uD83D\uDC7E", AppScreen.Social),
                Triple("Šport in aktivnost", "\uD83D\uDC5F", AppScreen.Sport),
                Triple("Splošno počutje", "\uD83E\uDEC2", AppScreen.Wellbeing)
            )
            CreateBottomMenu(
                expended = showNavMenu,
                onDismissRequest = { showNavMenu = false },
                itemsList = functionalityList,
                onItemClick = { state ->
                    navigationViewModel.changeToScreen(state)
                    showNavMenu = false
                }
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

            CreateProfileImage(
                modifier = Modifier.fillMaxSize()
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .clickable {showUserMenu = !showUserMenu },
                imgRes = R.drawable.user_menu,
                description = "User Menu"
            )

            val userMenuList = listOf(
                Triple("Uporabnik", "\uD83D\uDC64", AppScreen.User),
                Triple("Pregled vnosov","\uD83D\uDCC8", AppScreen.Data),
                Triple("Nastavitve", "⚙\uFE0F", AppScreen.Settings)
            )

            CreateBottomMenu(
                expended = showUserMenu,
                onDismissRequest = {showUserMenu = false},
                itemsList = userMenuList,
                onItemClick = { state ->
                    navigationViewModel.changeToScreen(state)
                    showUserMenu = false
                }
            )

        }
    }

}


@Composable
fun CreateBottomMenu(
    expended: Boolean,
    onDismissRequest: () -> Unit,
    itemsList: List<Triple<String, String, AppScreen>>,
    onItemClick: (AppScreen) -> Unit,
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
            .fillMaxWidth(0.8f)
    ) {

        itemsList.forEachIndexed { index, (label, emoji, state) ->
            DropdownMenuItem(
                text = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = emoji,
                            fontSize = 25.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = label,
                            fontFamily = roboto,
                            fontSize = 15.sp,
                            color = RetroTeal,
                            letterSpacing = 1.sp
                        )
                    }
                },
                onClick = {
                    onItemClick(state)
                },
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
            if (index != itemsList.lastIndex) {
                HorizontalDivider(
                    color = RetroPixelBorder.copy(alpha = 0.4f),
                    thickness = 1.dp
                )
            }
        }
    }
}

