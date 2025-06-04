package com.example.dzen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import com.example.dzen.nav.AppScreen
import com.example.dzen.ui.theme.DzenTheme
import com.example.dzen.ui.theme.screen.splash.SplashScreen
import com.example.dzen.ui.theme.screen.splash.SplashViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DzenTheme {
                val viewModel: SplashViewModel by viewModels()
                val isLoading = viewModel.isLoading.collectAsState()

                LaunchedEffect(key1 = viewModel) {
                    if (isLoading.value) {
                        delay(3000L)

                        viewModel.splashScreen()
                    }
                }

                val systemUiController = rememberSystemUiController()
                DisposableEffect(systemUiController) {
                    systemUiController.setSystemBarsColor(
                        color = Color.White,
                        darkIcons = true
                    )
                    onDispose {}
                }
                Crossfade(
                    targetState = isLoading.value,
                    animationSpec = tween(1500),
                    label = ""
                ) {
                    if (it) SplashScreen() else AppScreen()
                }
            }
        }
    }
}
