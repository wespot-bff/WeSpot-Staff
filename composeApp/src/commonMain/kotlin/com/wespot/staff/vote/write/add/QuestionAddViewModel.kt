package com.wespot.staff.vote.write.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.wespot.staff.domain.vote.VoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionAddViewModel(
    private val repository: VoteRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(QuestionAddUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<QuestionAddUiEvent>()
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
            state.copy(questionList = state.questionList + "")
        }
    }

    fun deleteQuestionItem(index: Int) {
        runCatching {
            _uiState.update { state ->
                val updatedList = state.questionList.toMutableList()
                updatedList.removeAt(index)
                state.copy(questionList = updatedList)
            }
        }.onFailure {

        }
    }

    fun submitQuestionList() {
        viewModelScope.launch {
            val questionList = _uiState.value.questionList.filter { it.isNotBlank() }
            Logger.d(questionList.toTypedArray().contentToString())
            /*repository.postVoteQuestions(questionList)
                .onSuccess {

                }
                .onFailure {

                }*/
        }
    }
}