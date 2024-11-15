package com.wespot.staff.vote.write.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.vote.VoteQuestionContent
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

    fun setQuestionList(questionList: List<VoteQuestionContent>) {
        _uiState.update { state ->
            state.copy(questionList = questionList)
        }
    }

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
        }
    }

    fun addQuestionItem() {
        _uiState.update { state ->
            state.copy(questionList = state.questionList + VoteQuestionContent())
        }
    }

    fun deleteQuestionItem(index: Int) {
        runCatching {
            _uiState.update { state ->
                val updatedList = state.questionList.toMutableList()
                updatedList.removeAt(index)
                state.copy(questionList = updatedList)
            }
        }
    }

    fun submitQuestionList() {
        viewModelScope.launch {
            val questionList = _uiState.value.questionList
                .filter { it.isNotBlank() }
                .map { it.trim() }
            repository.postVoteQuestions(questionList)
                .onSuccess {
                    _uiEvent.send(QuestionAddUiEvent.NavigateToQuestionScreen)
                }
                .onFailure { exception ->
                    _uiEvent.send(QuestionAddUiEvent.ShowToast("${exception.message} 문제가 발생했어요."))
                }
        }
    }
}
