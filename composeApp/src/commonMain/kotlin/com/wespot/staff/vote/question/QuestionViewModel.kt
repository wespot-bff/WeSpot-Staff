package com.wespot.staff.vote.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.vote.VoteQuestion
import com.wespot.staff.domain.vote.VoteRepository
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val voteRepository: VoteRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState = _uiState
        .onStart {
            getVoteQuestions()
            observeSearchInput()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _uiState.value
        )

    private val searchInput: MutableStateFlow<String> = MutableStateFlow("")
    private var voteQuestions: List<VoteQuestion> = listOf()

    private val _uiEvent = Channel<QuestionUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun getVoteQuestions() {
        viewModelScope.launch {
            voteRepository.getVoteQuestions()
                .onSuccess { questionList ->
                    _uiState.update { it.copy(questionList = questionList) }
                    voteQuestions = questionList
                }
                .onFailure { exception ->
                    _uiEvent.send(QuestionUiEvent.QuestionLoadFailedEvent)
                    Logger.e("HomeViewModel", exception)
                }
        }
    }

    fun setVoteQuestionInput(question: String) {
        _uiState.update { it.copy(questionInput = question) }
    }

    fun setQuestionClickedState(question: VoteQuestion) {
        _uiState.update {
            it.copy(
                clickedQuestion = question,
                questionInput = question.content,
            )
        }
    }

    fun clearQuestionClickedState() {
        _uiState.update {
            it.copy(
                clickedQuestion = VoteQuestion(),
                questionInput = "",
            )
        }
    }

    private fun observeSearchInput() {
        viewModelScope.launch(Dispatchers.Default) {
            searchInput
                .debounce(500)
                .distinctUntilChanged()
                .collect { keyword ->
                    if (_uiState.value.isSearchState) {
                        val list = voteQuestions.filter { keyword in it.content }
                        _uiState.update { it.copy(questionList = list) }
                    }
                }
        }
    }

    fun setSearchInput(keyword: String) {
        searchInput.value = keyword
        _uiState.update { it.copy(searchInput = keyword) }
    }

    fun toggleSearchState() {
        val previousState = _uiState.value.isSearchState
        if (previousState) {
            setSearchInput("")
            _uiState.update { it.copy(questionList = voteQuestions) }
        }

        _uiState.update { it.copy(isSearchState = previousState.not()) }
    }

    fun editVoteQuestion() {
        viewModelScope.launch {
            val id = _uiState.value.clickedQuestion.id
            val input = _uiState.value.questionInput.ifEmpty {
                _uiEvent.send(QuestionUiEvent.QuestionPostEvent("질문 내용을 입력해주세요."))
                return@launch
            }

            voteRepository.editVoteQuestion(id, input)
                .onSuccess {
                    _uiState.update { it.copy(questionInput = "") }
                    _uiEvent.send(QuestionUiEvent.QuestionPostEvent("질문이 수정되었습니다"))
                }.onFailure {
                    _uiEvent.send(QuestionUiEvent.QuestionPostEvent("질문 수정에 실패하였습니다"))
                }
        }
    }

    fun postVoteQuestion() {
        viewModelScope.launch {
            val input = _uiState.value.questionInput.ifEmpty {
                _uiEvent.send(QuestionUiEvent.QuestionPostEvent("질문 내용을 입력해주세요."))
                return@launch
            }

            voteRepository.postVoteQuestion(question = input)
                .onSuccess {
                  _uiState.update { it.copy(questionInput = "") }
                    _uiEvent.send(QuestionUiEvent.QuestionPostEvent("질문이 추가되었습니다"))
                }.onFailure {
                    _uiEvent.send(QuestionUiEvent.QuestionPostEvent("질문 추가에 실패하였습니다"))
                }
        }
    }
}
