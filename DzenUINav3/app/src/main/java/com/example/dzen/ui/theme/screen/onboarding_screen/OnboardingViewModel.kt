package com.example.dzen.ui.theme.screen.onboarding_screen

import androidx.lifecycle.ViewModel
import com.example.dzen.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnboardViewModel: ViewModel()  {
    private val _state = MutableStateFlow(OnboardState())
    val state = _state.asStateFlow()
}

data class OnboardState(
    val onBoardList: List<OnBoard> = OnBoard.imgData
)

data class OnBoard(val img: Int) {
    companion object {
        val imgData = listOf(
            OnBoard(R.drawable.rectagle),
            OnBoard(R.drawable.chikago_7),
            OnBoard(R.drawable.london),
            OnBoard(R.drawable.marson),
            OnBoard(R.drawable.rectagle),
        )
    }
}