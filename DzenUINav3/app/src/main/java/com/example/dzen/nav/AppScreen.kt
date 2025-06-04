@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.dzen.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.rememberNavBackStack
import com.example.dzen.ui.theme.screen.publication.PublicationViewModel

@Composable
fun AppScreen() {
    val navBackStack = rememberNavBackStack(OnBoardingScreen)
    val viewModel: PublicationViewModel = viewModel()
    val isBottomBarShow = navBackStack.lastOrNull() is OnBoardingScreen
    val isBottomBarShow1 = navBackStack.lastOrNull() is ArticleScreens

    Scaffold(
        bottomBar = {
            if (!isBottomBarShow && !isBottomBarShow1) {
                DzenBottomBar(
                    navBackStack = navBackStack,
                    modifier = Modifier,
                )
            }
        },
    ) { innerPadding ->
        MainNavBackStack(
            navBackStack = navBackStack,
            modifier = Modifier.padding(innerPadding),
            publicationViewModel = viewModel
        )
    }
}
