package com.example.dzen.ui.theme.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    val _isLoading = MutableStateFlow(true)
    var isLoading = _isLoading.asStateFlow()

    fun splashScreen() {
        _isLoading.value = false
    }

    init {
        viewModelScope.launch {
            delay(3000)
            _isLoading.value = false
        }
    }
}