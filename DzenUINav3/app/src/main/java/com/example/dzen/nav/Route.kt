package com.example.dzen.nav

import androidx.navigation3.runtime.NavKey
import com.example.dzen.R
import kotlinx.serialization.Serializable

@Serializable
sealed class HomeBottomBarNav(val icon: Int) : NavKey {

    @Serializable
    object HomeScreen : HomeBottomBarNav(R.drawable.vector__2_)

    @Serializable
    object CategorySearchScreen : HomeBottomBarNav(R.drawable.vector__5_)

    @Serializable
    object Bookmarks : HomeBottomBarNav(R.drawable.vector__3_)

    @Serializable
    object UserScreen : HomeBottomBarNav(R.drawable.vector__4_)
}

val bottomNavigationBarItems = listOf(
    HomeBottomBarNav.HomeScreen,
    HomeBottomBarNav.CategorySearchScreen,
    HomeBottomBarNav.Bookmarks,
    HomeBottomBarNav.UserScreen
)

@Serializable
sealed class UserNav : NavKey {

    @Serializable
    object LanguageScreen : UserNav()

    @Serializable
    object ChangePasswordScreen : UserNav()

    @Serializable
    object TermsConditionsScreen : UserNav()
}

@Serializable
data class ArticleScreens(
    val title: String,
    val subtitle: String,
    val imgRes: Int?,
    val imgUri: String? = null
) : NavKey

@Serializable
data object OnBoardingScreen : NavKey

@Serializable
data object CreatePublication : NavKey