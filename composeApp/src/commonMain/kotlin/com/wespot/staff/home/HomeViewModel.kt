package com.wespot.staff.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bff.wespot.staff.domain.vote.VoteQuestion
import bff.wespot.staff.domain.vote.VoteQuestionContent
import bff.wespot.staff.domain.vote.VoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val voteRepository: VoteRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getVoteQuestionList() {
        viewModelScope.launch {
            voteRepository.getVoteQuestions()
                .onSuccess { questions ->
                    _uiState.update { it.copy(questionList = questions) }
                }
                .onFailure {
                    _uiEvent.send(HomeUiEvent.QuestionLoadFailedEvent)
                }
        }
    }

    fun setVoteQuestionInput(question: String) {
        _uiState.update { it.copy(questionInput = question) }
    }

    fun setQuestionClickedState(question: VoteQuestion) {
        _uiState.update { it.copy(clickedQuestion = question) }
    }

    fun postVoteQuestion() {
        viewModelScope.launch {
            val input = _uiState.value.questionInput.ifEmpty {
                _uiEvent.send(HomeUiEvent.QuestionPostEvent("질문 내용을 입력해주세요."))
                return@launch
            }

            voteRepository.postVoteQuestion(question = input)
                .onSuccess {
                  _uiState.update { it.copy(questionInput = "") }
                    _uiEvent.send(HomeUiEvent.QuestionPostEvent("질문이 추가되었습니다"))
                }.onFailure {
                    _uiEvent.send(HomeUiEvent.QuestionPostEvent("질문 추가에 실패하였습니다"))
                }
        }
    }
}
