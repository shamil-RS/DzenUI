package com.example.dzen.ui.theme.screen.publication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dzen.model.Publication
import com.example.dzen.ui.theme.screen.main_screen.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PublicationViewModel : ViewModel() {
    private val _publications = MutableStateFlow(Publication.TheNews.newsData.toMutableList())
    val publications: StateFlow<List<Publication.TheNews>> = _publications.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    fun setSelectedCategory(category: Category) {
        _selectedCategory.value = category
    }

    fun createPublication(title: String, subtitle: String, imgUri: String) {
        viewModelScope.launch {
            val newPublication = Publication.TheNews(
                title = title,
                subtitle = subtitle,
                imgUri = imgUri,
                isFavorite = false
            )
            _publications.value += newPublication
        }
    }
}