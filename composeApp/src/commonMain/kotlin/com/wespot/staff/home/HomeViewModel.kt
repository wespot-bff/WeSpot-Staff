package com.wespot.staff.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.vote.VoteQuestion
import com.wespot.staff.domain.vote.VoteRepository
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val voteRepository: VoteRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val searchInput: MutableStateFlow<String> = MutableStateFlow("")
    private val questionListFlow: StateFlow<List<VoteQuestion>> =
        voteRepository.getVoteQuestionsStream()
            .catch { exception ->
                _uiEvent.send(HomeUiEvent.QuestionLoadFailedEvent)
                Logger.e("HomeViewModel", exception)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = emptyList()
            )

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun observeVoteQuestionsStream() {
        viewModelScope.launch {
            questionListFlow.collect { questionList ->
                _uiState.update { it.copy(questionList = questionList) }
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

    fun setSearchInput(keyword: String) {
        searchInput.value = keyword
        _uiState.update { it.copy(searchInput = keyword) }
    }

    fun observeSearchInput() {
        viewModelScope.launch(Dispatchers.Default) {
            searchInput
                .debounce(500)
                .distinctUntilChanged()
                .collect { keyword ->
                    if (_uiState.value.isSearchState) {
                        val list = questionListFlow.value.filter { keyword in it.content }
                        _uiState.update { it.copy(questionList = list) }
                    }
                }
        }
    }

    fun toggleSearchState() {
        val previousState = _uiState.value.isSearchState
        if (previousState) {
            setSearchInput("")
            _uiState.update { it.copy(questionList = questionListFlow.value) }
        }

        _uiState.update { it.copy(isSearchState = previousState.not()) }
    }

    fun editVoteQuestion() {
        viewModelScope.launch {
            val id = _uiState.value.clickedQuestion.id
            val input = _uiState.value.questionInput.ifEmpty {
                _uiEvent.send(HomeUiEvent.QuestionPostEvent("질문 내용을 입력해주세요."))
                return@launch
            }

            voteRepository.editVoteQuestion(id, input)
                .onSuccess {
                    _uiState.update { it.copy(questionInput = "") }
                    _uiEvent.send(HomeUiEvent.QuestionPostEvent("질문이 수정되었습니다"))
                }.onFailure {
                    _uiEvent.send(HomeUiEvent.QuestionPostEvent("질문 수정에 실패하였습니다"))
                }
        }
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
