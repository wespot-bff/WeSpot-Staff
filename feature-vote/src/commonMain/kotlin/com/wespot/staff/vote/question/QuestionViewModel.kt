package com.wespot.staff.vote.question

import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.vote.VoteQuestion
import com.wespot.staff.domain.vote.VoteRepository
import com.wespot.staff.common.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class QuestionViewModel(
    private val voteRepository: VoteRepository,
): BaseViewModel<QuestionUiState, QuestionSideEffect>() {
    override fun createInitialState(): QuestionUiState = QuestionUiState()

    private val searchInput: MutableStateFlow<String> = MutableStateFlow("")
    private var voteQuestions: List<VoteQuestion> = listOf()

    init {
        getVoteQuestions()
        observeSearchInput()
    }

    private fun getVoteQuestions() {
        viewModelScope.launch {
            reduce { copy(isLoading = true) }
            voteRepository.getVoteQuestions()
                .onSuccess { questionList ->
                    reduce { copy(questionList = questionList) }
                    voteQuestions = questionList
                }
                .onFailure { exception ->
                    postSideEffect(QuestionSideEffect.QuestionLoadFailedEvent)
                }.also {
                    reduce { copy(isLoading = false) }
                }
        }
    }

    private fun observeSearchInput() {
        viewModelScope.launch(Dispatchers.Default) {
            searchInput
                .debounce(500)
                .distinctUntilChanged()
                .collect { keyword ->
                    if (state.isSearchState) {
                        val list = voteQuestions.filter { keyword in it.content }
                        reduce { copy(questionList = list) }
                    }
                }
        }
    }

    fun setVoteQuestionInput(question: String) {
        reduce { copy(questionInput = question) }
    }

    fun setQuestionClickedState(question: VoteQuestion) {
        reduce {
            copy(
                clickedQuestion = question,
                questionInput = question.content,
            )
        }
    }

    fun setSearchInput(keyword: String) {
        searchInput.value = keyword
        reduce { copy(searchInput = keyword) }
    }

    fun toggleSearchState() {
        val previousState = state.isSearchState
        if (previousState) {
            setSearchInput("")
            reduce { copy(questionList = voteQuestions) }
        }

        reduce { copy(isSearchState = previousState.not()) }
    }

    fun editVoteQuestion() {
        viewModelScope.launch {
            val id = state.clickedQuestion.id
            val input = state.questionInput.ifEmpty {
                postSideEffect(QuestionSideEffect.QuestionPostEvent("질문 내용을 입력해주세요."))
                return@launch
            }

            reduce { copy(isLoading = true) }
            voteRepository.editVoteQuestion(id, input)
                .onSuccess {
                    clearQuestionClickedState()
                    getVoteQuestions()
                    postSideEffect(QuestionSideEffect.QuestionPostEvent("질문이 수정되었습니다"))
                }.onFailure {
                    postSideEffect(QuestionSideEffect.QuestionPostEvent("질문 수정에 실패하였습니다"))
                }.also {
                    reduce { copy(isLoading = false) }
                }
        }
    }

    fun postVoteQuestion() {
        viewModelScope.launch {
            val input = state.questionInput.ifEmpty {
                postSideEffect(QuestionSideEffect.QuestionPostEvent("질문 내용을 입력해주세요."))
                return@launch
            }

            reduce { copy(isLoading = true) }
            voteRepository.postVoteQuestion(question = input)
                .onSuccess {
                    clearQuestionClickedState()
                    getVoteQuestions()
                    postSideEffect(QuestionSideEffect.QuestionPostEvent("질문이 추가되었습니다"))
                }.onFailure {
                    postSideEffect(QuestionSideEffect.QuestionPostEvent("질문 추가에 실패하였습니다"))
                }.also {
                    reduce { copy(isLoading = false) }
                }
        }
    }

    fun clearQuestionClickedState() {
        reduce {
            copy(
                clickedQuestion = VoteQuestion(),
                questionInput = "",
            )
        }
    }
}
