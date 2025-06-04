package com.example.dzen.ui.theme.screen.user_screen

import androidx.lifecycle.ViewModel
import com.example.dzen.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private var _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    fun notificationOnOff() {
        with(_state) {
            update {
                it.copy(isCheckBox = !it.isCheckBox)
            }
        }
    }
}

data class ProfileUiState(
    val listFunctionBtn: List<UserProfile> = UserProfile.userProfileData,
    var isCheckBox: Boolean = false
)

data class UserProfile(
    val id: Int = 0,
    val title: String = "",
    val icon: Int = R.drawable.vpered,
    val onClick: () -> Unit = {},
) {
    companion object {
        val userProfileData = listOf(
            UserProfile(1, "Language"),
            UserProfile(2, "Change password"),
            UserProfile(3, "Privacy"),
            UserProfile(4, "Terms & Conditions"),
        )
    }
}