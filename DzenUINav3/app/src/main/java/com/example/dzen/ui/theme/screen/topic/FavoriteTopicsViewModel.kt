package com.example.dzen.ui.theme.screen.topic

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.dzen.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FavoriteTopicsViewModel : ViewModel() {

    private val _state = MutableStateFlow(FavoriteTopicUiState())
    val state = _state.asStateFlow()

    fun selectedTopic(newTopic: Topics) {
        with(_state) {
            val newListTopic = value.topicList.map { topic ->
                if (newTopic.id == topic.id) topic.copy(isSelected = !topic.isSelected)
                else topic
            }

            update { topic ->
                topic.copy(topicList = newListTopic)
            }
        }
    }
}

data class FavoriteTopicUiState(
    val topicList: List<Topics> = Topics.mockData
)

data class Topics(
    val id: Int = 0,
    val title: String = "",
    val isSelected: Boolean = false,
    val color: Color = Color.White,
    val icon: Int = 0,
) {
    companion object {
        val mockData = listOf(
            Topics(1, "Sports", icon = R.drawable._69americanfootball_100217),
            Topics(2, "Politics", icon = R.drawable._497562278_libra_zodiac_sign_85081),
            Topics(3, "Life", icon = R.drawable._2654sunwithface_99097),
            Topics(4, "Gaming", icon = R.drawable.gamepad_115834_115795),
            Topics(5, "Animal", icon = R.drawable._2259bearface_98743),
            Topics(6, "Nature", icon = R.drawable._2331palmtree_98823),
            Topics(7, "Food", icon = R.drawable.symbol_emoj_food_fastfood_burger_hamburger_icon_210591),
            Topics(8, "Art", icon = R.drawable.art_pallete_102830),
            Topics(9, "History", icon = R.drawable.manuscript_history_log_icon_232917),
            Topics(10, "Fashion", icon = R.drawable._2183dress_109567),
        )
    }
}