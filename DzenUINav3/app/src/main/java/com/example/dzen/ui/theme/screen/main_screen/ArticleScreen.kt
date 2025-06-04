@file:OptIn(ExperimentalMaterialApi::class)

package com.example.dzen.ui.theme.screen.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation3.runtime.NavBackStack
import coil.compose.AsyncImage
import com.example.dzen.R

@Composable
fun ArticleScreen(
    navBackStack: NavBackStack,
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    imgRes: Int?,
    imgUri: String? = null
) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Result",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = Color(0xFF666C8E),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetPeekHeight = 420.dp,
        sheetBackgroundColor = Color.Black,
    ) {
        Column(
            modifier = modifier.size(430.dp, 394.dp)
        ) {
            ConstraintLayout(modifier = Modifier) {
                val (arrowBack, bookmarks, share, categoryRef, titleRef, imgProfile, name, subtitleRef) = createRefs()
                if (imgRes != null) {
                    Image(
                        painter = painterResource(id = imgRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
                else if (!imgUri.isNullOrEmpty()) {
                    AsyncImage(
                        model = imgUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.rowback),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .constrainAs(arrowBack) {
                            start.linkTo(parent.start, margin = 20.dp)
                            top.linkTo(parent.top, margin = 28.dp)
                        }
                        .size(18.dp)
                        .clickable { navBackStack.removeLastOrNull() }
                )
                Icon(
                    painter = painterResource(id = R.drawable.vector__7_),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.constrainAs(bookmarks) {
                        end.linkTo(parent.end, margin = 20.dp)
                        top.linkTo(parent.top, margin = 28.dp)
                    }
                )
                Icon(
                    painter = painterResource(id = R.drawable.share_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.constrainAs(share) {
                        end.linkTo(parent.end, margin = 18.dp)
                        top.linkTo(bookmarks.top, margin = 40.dp)
                    }
                )
                Category(
                    text = title,
                    isSelected = true,
                    modifier = Modifier
                        .wrapContentSize()
                        .widthIn(min = 52.dp)
                        .heightIn(min = 24.dp)
                        .constrainAs(categoryRef) {
                            start.linkTo(parent.start, margin = 4.dp)
                            top.linkTo(arrowBack.top, margin = 140.dp)
                        }
                        .size(width = 100.dp, height = 40.dp)
                )
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(titleRef) {
                        start.linkTo(parent.start, margin = 20.dp)
                        top.linkTo(categoryRef.top, margin = 46.dp)
                    }
                )

                Image(
                    painter = painterResource(id = R.drawable.ellipse),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(imgProfile) {
                            start.linkTo(parent.start, margin = 20.dp)
                            top.linkTo(titleRef.top, margin = 84.dp)
                        }
                        .size(48.dp)
                )
                Text(
                    text = "John Doe", color = Color.White, fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.constrainAs(name) {
                        start.linkTo(imgProfile.start, margin = 60.dp)
                        top.linkTo(titleRef.top, margin = 84.dp)
                    }
                )
                Text(
                    text = truncateText(subtitle, 4 * 6),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFACAFC3), fontSize = 14.sp,
                    modifier = Modifier.constrainAs(subtitleRef) {
                        start.linkTo(imgProfile.start, margin = 60.dp)
                        top.linkTo(name.top, margin = 24.dp)
                    }
                )
            }
        }
    }
}

fun truncateText(text: String, maxWords: Int): String {
    val words = text.split("")

    return if (words.size <= maxWords) {
        text
    } else {
        words.take(maxWords).joinToString("") + "..."
    }
}