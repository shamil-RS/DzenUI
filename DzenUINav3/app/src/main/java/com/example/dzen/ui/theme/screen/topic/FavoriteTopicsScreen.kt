@file:OptIn(ExperimentalLayoutApi::class)

package com.example.dzen.ui.theme.screen.topic

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun TopicScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteTopicsViewModel,
    state: FavoriteTopicUiState
) {
    with(state) {
        Column(
            modifier = modifier
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Select your favorite topics",
                modifier = Modifier.padding(vertical = 28.dp),
                color = Color.Black,
                fontSize = 24.sp,
                fontFamily = FontFamily.Monospace,
            )
            Text(
                text = "Select some of your favorite topics to let us suggest better news for you.",
                color = Color.Gray,
                fontSize = 14.sp,
                fontFamily = FontFamily.Monospace,
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                topicList.map { topic ->
                    TopicBlock(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = topic.title,
                        icon = topic.icon,
                        isSelected = topic.isSelected,
                        onClick = { viewModel.selectedTopic(topic) }
                    )
                }
            }
            Button(
                modifier = Modifier
                    .padding(bottom = 150.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF475ad7)),
                shape = RoundedCornerShape(12.dp),
                onClick = {}
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TopicBlock(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
    icon: Int
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF475ad7) else Color(0xFFf3f4f6)
    )

    Box(
        modifier = modifier
            .size(160.dp, 72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
            )
            Text(
                text = text,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace,
            )
        }
    }
}