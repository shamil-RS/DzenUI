package com.example.dzen.nav

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import com.example.dzen.R

@Composable
fun DzenBottomBar(
    navBackStack: NavBackStack,
    modifier: Modifier = Modifier,
) {

    val currentScreen = navBackStack.lastOrNull()

    BottomAppBar(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp)),
        elevation = 10.dp,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 44.dp)
                .selectableGroup(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = CenterVertically
        ) {
            bottomNavigationBarItems.forEach { screen ->
                BottomBarItem(
                    icon = screen.icon,
                    selected = currentScreen == screen,
                    onClick = { navBackStack.add(screen) }
                )
            }
        }
    }
}

@Composable
fun BottomBarItem(
    @DrawableRes icon: Int = R.drawable.vector__2_,
    onClick: () -> Unit = {},
    selected: Boolean = false,
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            ),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Icon(
            modifier = Modifier
                .align(CenterHorizontally)
                .size(22.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (selected) Color(0xFF475ad7) else Color.Gray
        )
    }
}