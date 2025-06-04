package com.example.dzen.ui.theme.screen.user_screen.component

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LanguageViewModel : ViewModel() {

    private var _state = MutableStateFlow(LanguageUiState())
    val state = _state.asStateFlow()

    fun languageSelected(language: Language) {
        with(_state) {
            val newLanguageList = value.languageList.map { lang ->
                if (lang.id == language.id) lang.copy(isChecked = !lang.isChecked)
                else lang
            }

            update {
                it.copy(languageList = newLanguageList)
            }
        }
    }
}

data class LanguageUiState(
    val languageList: List<Language> = Language.languageData
)

data class Language(
    val id: Int = 0,
    val title: String = "",
    var isChecked: Boolean = false,
    val icon: Int = 0,
) {
    companion object {
        val languageData = listOf(
            Language(1, "English"),
            Language(2, "Turkish"),
            Language(3, "German"),
            Language(4, "Spanish"),
        )
    }
}