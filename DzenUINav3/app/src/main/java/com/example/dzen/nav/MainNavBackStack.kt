package com.example.dzen.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.dzen.ui.theme.screen.bookmarks.BookmarksScreen
import com.example.dzen.ui.theme.screen.main_screen.ArticleScreen
import com.example.dzen.ui.theme.screen.main_screen.MainScreen
import com.example.dzen.ui.theme.screen.main_screen.MainScreenViewModel
import com.example.dzen.ui.theme.screen.publication.CreatePublicationScreen
import com.example.dzen.ui.theme.screen.publication.PublicationViewModel
import com.example.dzen.ui.theme.screen.topic.FavoriteTopicsViewModel
import com.example.dzen.ui.theme.screen.topic.TopicScreen
import com.example.dzen.ui.theme.screen.user_screen.UserProfileScreen

@Composable
fun MainNavBackStack(
    modifier: Modifier = Modifier,
    navBackStack: NavBackStack,
    publicationViewModel: PublicationViewModel
) {
    NavDisplay(
        backStack = navBackStack,
        onBack = { navBackStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = entryProvider {

            onBoardNavEntries(navBackStack)

            entry<HomeBottomBarNav.HomeScreen> {
                val viewModel: MainScreenViewModel = viewModel()
                val uiState by viewModel.state.collectAsState()

                MainScreen(
                    navBackStack = navBackStack,
                    state = uiState,
                    viewModel = viewModel,
                    publicationViewModel = publicationViewModel
                )
            }

            entry<CreatePublication> {
                CreatePublicationScreen(
                    publicationViewModel = publicationViewModel,
                    onPublicationAdded = { navBackStack.removeLastOrNull() }
                )
            }

            entry<ArticleScreens> { screen ->
                ArticleScreen(
                    title = screen.title,
                    subtitle = screen.subtitle,
                    imgUri = screen.imgUri,
                    imgRes = screen.imgRes,
                    navBackStack = navBackStack
                )
            }

            entry<HomeBottomBarNav.CategorySearchScreen> {
                val viewModel: FavoriteTopicsViewModel = viewModel()
                val uiState by viewModel.state.collectAsState()

                TopicScreen(viewModel = viewModel, state = uiState)
            }

            entry<HomeBottomBarNav.Bookmarks> { BookmarksScreen() }

            entry<HomeBottomBarNav.UserScreen> {
                UserProfileScreen(navBackStack = navBackStack)
            }

            addUserNavEntries(navBackStack)
        }
    )
}
