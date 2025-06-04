@file:OptIn(
    ExperimentalPagerApi::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)

package com.example.dzen.ui.theme.screen.onboarding_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import com.example.dzen.nav.HomeBottomBarNav
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlin.math.abs
import kotlin.math.min

private const val MULTIPLIER_SELECTED_PAGE = 2
private val baseWidth = 8.dp
private val spacing = 10.dp
private val height = 8.dp

@Preview
@Composable
fun OnboardScreen(
    modifier: Modifier = Modifier,
    navBackStack: NavBackStack = NavBackStack()
) {

    val viewModel: OnboardViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val pagerState = rememberPagerState()
    val transition by animateFloatAsState(
        targetValue = if (true) 1f else 0f, label = ""
    )

    with(state) {
        Column(
            modifier = modifier.padding(vertical = 30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                count = onBoardList.size,
                itemSpacing = 5.dp,
                state = pagerState,
                contentPadding = PaddingValues(),
            ) { page ->
                val onBoard = onBoardList[page]
                Box(
                    modifier = Modifier
                        .size(288.dp, 336.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    content = {
                        Image(
                            painter = painterResource(onBoard.img),
                            modifier = Modifier
                                .fillMaxSize()
                                .scale(.8f + (.2f * transition))
                                .graphicsLayer { rotationX = (1f - transition) * 5f }
                                .alpha(min(1f, transition / .2f)),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 30.dp))

            CustomPagerIndicator(pagerState = pagerState)

            Text(
                text = "First to know",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Text(
                text = "All news in one place, be\n the first to know last news",
                fontSize = 18.sp,
                color = Color(0xFF8c92ad),
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(56.dp),
                onClick = { navBackStack.add(HomeBottomBarNav.HomeScreen) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF475ad7))
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CustomPagerIndicator(pagerState: PagerState) {

    val indicatorColor = Color(0xFF475ad7)

    Row {
        val offsetIntPart = pagerState.currentPageOffset.toInt()
        val offsetFractionalPart = pagerState.currentPageOffset - offsetIntPart
        val currentPage = pagerState.currentPage + offsetIntPart
        val targetPage = if (pagerState.currentPageOffset < 0) currentPage - 1 else currentPage + 1
        val currentPageWidth =
            baseWidth * (1 + (1 - abs(offsetFractionalPart)) * MULTIPLIER_SELECTED_PAGE)
        val targetPageWidth = baseWidth * (1 + abs(offsetFractionalPart) * MULTIPLIER_SELECTED_PAGE)

        repeat(pagerState.pageCount) { index ->
            val width = when (index) {
                currentPage -> currentPageWidth
                targetPage -> targetPageWidth
                else -> baseWidth
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .width(width)
                    .background(if (index == currentPage) indicatorColor else Color(0xFF8c92ad))
                    .height(height)
            )
            if (index != pagerState.pageCount - 1) {
                Spacer(modifier = Modifier.width(spacing))
            }
        }
    }
}