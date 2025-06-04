package com.example.dzen.ui.theme.screen.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dzen.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainViewState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categoryList: List<Category> = emptyList(),
    val recommendedList: List<Recommended> = emptyList(),
    val isSelected: Boolean = false,
)

sealed class MainIntent {
    object LoadData : MainIntent()
    data class CategorySelected(val category: Category) : MainIntent()
}

class MainScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainViewState(isLoading = true))
    val state: StateFlow<MainViewState> = _state.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.LoadData -> loadData()
            is MainIntent.CategorySelected -> categorySelected(intent.category)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            Log.d("MainScreenViewModel", "Loading data...")
            try {
                delay(2000L)

                val categoryList = Category.mockData
                val recommendedList = Recommended.recommended
                _state.value = MainViewState(
                    categoryList = categoryList,
                    recommendedList = recommendedList
                )
                Log.d("MainScreenViewModel", "Data loaded successfully")
            } catch (e: Exception) {
                _state.value = _state.value.copy(errorMessage = e.message)
                Log.e("MainScreenViewModel", "Error loading data: ${e.message}")
            } finally {
                _state.value = _state.value.copy(isLoading = false) // Завершаем загрузку
                Log.d("MainScreenViewModel", "Loading finished, isLoading set to false")
            }
        }
    }

    private fun categorySelected(newCategory: Category) {
        _selectedCategory.value = newCategory

        val updatedCategories = _state.value.categoryList.map { category ->
            if (category.id == newCategory.id) {
                category.copy(isSelected = !category.isSelected)
            } else {
                category.copy(isSelected = false)
            }
        }

        _state.value = _state.value.copy(categoryList = updatedCategories)
        _state.update {
            it.copy(
                categoryList = updatedCategories
            )
        }
    }
}

data class Category(
    val id: Int = 0,
    val title: String = "",
    var isSelected: Boolean = false,
    val previousButton: Boolean = false
) {
    companion object {
        val mockData = listOf(
            Category(1, title = "All", isSelected = true),
            Category(2, title = "Random"),
            Category(3, title = "Sport"),
            Category(4, title = "Gaming"),
            Category(5, title = "Politics"),
            Category(6, title = "Nature"),
            Category(7, title = "Education"),
            Category(8, title = "Last news"),
            Category(9, title = "Food"),
            Category(10, title = "Art"),
        )
    }
}

data class Recommended(
    val title: String = "",
    val subtitle: String = "",
    val img: Int = 0,
) {
    companion object {
        val recommended = listOf(
            Recommended(
                "UI/UX Design",
                "A Simple Trick For Creating Color Palettes Quickly",
                R.drawable.rectangle11
            ),
            Recommended(
                "Art",
                "Six steps to creating a Color Palettes Quickly",
                R.drawable.rectangle10
            ),
            Recommended(
                "Colors",
                "Creating Color Palette from world around you",
                R.drawable.rectangle12
            ),
        )
    }
}