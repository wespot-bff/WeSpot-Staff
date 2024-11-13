package com.wespot.staff.vote.write

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.vote.VoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionWriteViewModel(
    private val repository: VoteRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(QuestionWriteUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<QuestionWriteUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setQuestionItem(index: Int, question: String) {
        runCatching {
            _uiState.update { state ->
                val updatedList = state.questionList.toMutableList().apply {
                    if (index in indices) {
                        this[index] = question
                    }
                }
                state.copy(questionList = updatedList)
            }
        }.onFailure {

        }
    }

    fun addQuestionItem() {
        _uiState.update { state ->
            val updatedList = state.questionList
            updatedList.add("")
            state.copy(questionList = updatedList)
        }
    }

    fun deleteQuestionItem(index: Int) {
        runCatching {
            _uiState.update { state ->
                val updatedList = state.questionList
                updatedList.removeAt(index)
                state.copy(questionList = updatedList)
            }
        }.onFailure {

        }
    }

    fun submitQuestionList() {
        viewModelScope.launch {
            val questionList = _uiState.value.questionList.filter { it.isNotBlank() }
            repository.postVoteQuestions(questionList)
                .onSuccess {

                }
                .onFailure {

                }
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