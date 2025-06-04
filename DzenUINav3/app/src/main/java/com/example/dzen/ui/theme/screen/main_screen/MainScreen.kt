package com.example.dzen.ui.theme.screen.main_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.net.toUri
import androidx.navigation3.runtime.NavBackStack
import com.example.dzen.R
import com.example.dzen.model.Publication
import com.example.dzen.nav.ArticleScreens
import com.example.dzen.nav.CreatePublication
import com.example.dzen.ui.theme.screen.publication.PublicationViewModel
import java.io.InputStream

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel,
    publicationViewModel: PublicationViewModel,
    state: MainViewState,
    navBackStack: NavBackStack
) {
    val publications by publicationViewModel.publications.collectAsState()

    val selectedCategory by viewModel.selectedCategory.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.handleIntent(MainIntent.LoadData) // Инициализация данных
    }

    when {
        state.isLoading -> LoadingScreen()
        state.errorMessage != null -> Text(text = "Error: ${state.errorMessage}")
        else -> {
            Column(
                modifier = modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Browse",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp
                )
                Text(
                    text = "Discover things of this world",
                    color = Color(0xFF7C82A1),
                    fontFamily = FontFamily.Monospace
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                SearchBar()
                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                LazyRow(
                    modifier = Modifier,
                    userScrollEnabled = true,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.categoryList, key = { it.id }) { category ->
                        Category(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = category.title,
                            isSelected = category.isSelected,
                            onClick = {
                                viewModel.handleIntent(MainIntent.CategorySelected(category))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                TextButton(onClick = { navBackStack.add(CreatePublication) }) {
                    Text(text = "New Publication")
                }

                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val filteredNews = publications.filter { news ->
                        selectedCategory?.let { category ->
                            if (category.title == "All") true
                            else news.title.contains(category.title, ignoreCase = true)
                        } != false
                    }

                    // Проверка на наличие фильтруемых новостей
                    if (filteredNews.isNotEmpty()) {
                        filteredNews.forEach { news ->
                            TheNewsScreen(
                                news = news,
                                onClick = {
                                    navBackStack.add(
                                        ArticleScreens(
                                            news.title,
                                            news.subtitle,
                                            news.imgRes,
                                            news.imgUri
                                        )
                                    )
                                },
                            )
                        }
                    } else {
                        Text(text = "No news available for this category.")
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                // Рекомендации
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = CenterVertically
                ) {
                    Text(
                        text = "Recommended for you",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "See All",
                        color = Color(0xFF7C82A1),
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Column(
                    modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    state.recommendedList.forEach { rec ->
                        RecommendedScreen(
                            title = rec.title,
                            subtitle = rec.subtitle,
                            img = rec.img
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TheNewsScreen(
    modifier: Modifier = Modifier,
    news: Publication.TheNews,
    onClick: () -> Unit = {},
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .size(256.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
    ) {
        ConstraintLayout(modifier = Modifier) {
            val (titles, subtitles) = createRefs()

            news.imgUri?.let { uri ->
                val bitmap = loadBitmapFromUri(context, uri)
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } ?: run {
                news.imgRes?.let { res ->
                    Image(
                        painter = painterResource(id = res),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Text(
                text = news.title,
                color = Color.White,
                modifier = Modifier.constrainAs(titles) {
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(subtitles.top, margin = 2.dp)
                }
            )
            Text(
                text = news.subtitle,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.constrainAs(subtitles) {
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )
        }
    }
}

private fun loadBitmapFromUri(context: Context, uri: String): Bitmap? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri.toUri())
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Center,
        content = { CircularProgressIndicator() }
    )
}

@Composable
fun RecommendedScreen(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String, img: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier, verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFF7C82A1),
                fontSize = 16.sp,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = subtitle,
                color = Color(0xFF333647),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFf3f4f6))
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search__2__1),
                contentDescription = null,
                tint = Color(0xFF7c82a1)
            )
            Text(
                text = "Search", color = Color(0xFF7c82a1), fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(horizontal = 90.dp))
            Icon(
                painter = painterResource(id = R.drawable.voice),
                contentDescription = null,
                tint = Color(0xFF7c82a1)
            )
        }
    }
}

@Composable
fun Category(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit = {},
) {
    val backgroundColor = if (isSelected) Color(0xFF475AD7) else Color(0xFFF3F4F6)
    val textColor = if (isSelected) Color.White else Color.Gray

    // Общий модификатор для Box
    val boxModifier = modifier
        .wrapContentSize()
        .widthIn(min = 52.dp)
        .heightIn(min = 24.dp)
        .clip(RoundedCornerShape(24.dp))
        .background(backgroundColor)
        .clickable { onClick() }

    Box(
        modifier = boxModifier,
        contentAlignment = Center
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }

    // Добавляем Divider только для категории "All"
    if (text == "All") {
        VerticalDivider(
            modifier = Modifier
                .size(2.dp, 36.dp),
            thickness = 1.dp,
            color = Color(0xFFEBEBEB)
        )
    }
}
