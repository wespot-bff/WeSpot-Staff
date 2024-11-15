package com.wespot.staff.vote.write.parse

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class QuestionParseViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionParseUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<QuestionParseUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setQuestionListString(questionListString: String) {
        _uiState.update { state ->
            state.copy(questionListString = questionListString)
        }
    }

    fun parseQuestionListString() {
        _uiState.update { state ->
            val questionList = state.questionListString
                .split("\n")
                .filter { it.isNotBlank() }
                .map { it.trim() }
                .toMutableList()

            state.copy(questionList = questionList)
        }
    }
}
