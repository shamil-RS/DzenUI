package com.example.dzen.model

import com.example.dzen.R

sealed interface Publication {
    val id: Int
    val title: String
    val subtitle: String
    val imgUri: String? // Хранение URI изображения
    val imgRes: Int? // Хранение идентификатора ресурса
    val isFavorite: Boolean

    data class TheNews(
        override val id: Int = 0,
        override val title: String,
        override val subtitle: String,
        override val imgUri: String? = null, // Добавляем URI
        override val imgRes: Int? = null, // Добавляем идентификатор ресурса
        override val isFavorite: Boolean = false
    ) : Publication {
        companion object {
            val newsData = listOf(
                TheNews(
                    id = 1,
                    title = "Politics",
                    subtitle = "The latest situation in the presidential election",
                    imgRes = R.drawable.rectangles,
                    isFavorite = false
                ),
                TheNews(
                    id = 2,
                    title = "Food",
                    subtitle = "Vegan burgers are taking over the world",
                    imgRes = R.drawable.food,
                    isFavorite = false
                ),
                TheNews(
                    id = 3,
                    title = "Art",
                    subtitle = "AI-Generated Art Breaks Records at International Auction",
                    imgRes = R.drawable.art_ai,
                    isFavorite = false
                ),
                TheNews(
                    id = 4,
                    title = "Random",
                    subtitle = "The latest situation in the presidential election",
                    imgRes = R.drawable.chikago_7,
                    isFavorite = false
                ),
                TheNews(
                    id = 5,
                    title = "Sport",
                    subtitle = "The latest 3",
                    imgRes = R.drawable.london,
                    isFavorite = false
                ),
                TheNews(
                    id = 6,
                    title = "Sport",
                    subtitle = "The latest 4",
                    imgRes = R.drawable.rectagle,
                    isFavorite = false
                ),
            )
        }
    }
}