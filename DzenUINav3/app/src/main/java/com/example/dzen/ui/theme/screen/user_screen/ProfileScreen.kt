@file:OptIn(ExperimentalAnimationApi::class)

package com.example.dzen.ui.theme.screen.user_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import com.example.dzen.R
import com.example.dzen.nav.UserNav

@Composable
fun UserProfileScreen(
    navBackStack: NavBackStack,
    modifier: Modifier = Modifier
) {
    val viewModel: ProfileViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    with(state) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 30.dp),
        ) {
            Text(
                text = "Profile",
                color = Color.Black,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ImageProfile()
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Username",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Email",
                        color = Color(0xFF7C82A1)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 30.dp))
            NotificationOnOff(
                isChecked = isCheckBox,
                onClick = { viewModel.notificationOnOff() }
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                listFunctionBtn.forEach { functionBtn ->
                    FunctionsButton(
                        text = functionBtn.title,
                        icon = functionBtn.icon,
                        onClick = {
                            when (functionBtn.id) {
                                1 -> navBackStack.add(UserNav.LanguageScreen)
                                2 -> navBackStack.add(UserNav.ChangePasswordScreen)
                                3 -> {}
                                4 -> navBackStack.add(UserNav.TermsConditionsScreen)
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            LogOutScreen(
                text = "LogOut",
                icon = R.drawable.log_out,
                onClick = {}
            )
        }
    }
}

@Composable
fun ImageProfile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(RoundedCornerShape(32.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ellipse),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun LogOutScreen(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF3F4F6))
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = text,
                color = Color(0xFF666C8E),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                modifier = Modifier,
                textAlign = TextAlign.Start,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color(0xFF666C8E),
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun FunctionsButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF3F4F6))
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = text,
                color = Color(0xFF666C8E),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                modifier = Modifier,
                textAlign = TextAlign.Start,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color(0xFF666C8E),
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun NotificationOnOff(
    modifier: Modifier = Modifier,
    text: String = "Notifications",
    isChecked: Boolean,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF3F4F6))
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = text,
                color = Color(0xFF666C8E),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                fontSize = 18.sp,
                modifier = Modifier,
                textAlign = TextAlign.Start,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SwitchBox(isChecked = isChecked, onClick = { onClick() })
            }
        }
    }
}

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@Composable
fun SwitchBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onClick: () -> Unit = {}
) {

    val backgroundColor = if (isChecked) Color(0xFF475ad7) else Color.DarkGray

    Box(
        modifier = modifier
            .size(40.dp, 30.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .background(backgroundColor),
        contentAlignment = if (isChecked) Alignment.TopEnd else Alignment.TopStart
    ) {
        AnimatedContent(targetState = isChecked) {
            Box(
                modifier = modifier
                    .padding(2.dp)
                    .size(20.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White),
            )
        }
    }
}