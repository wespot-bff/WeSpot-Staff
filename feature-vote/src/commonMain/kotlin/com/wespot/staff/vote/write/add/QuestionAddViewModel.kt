package com.wespot.staff.vote.write.add

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.vote.VoteQuestionContent
import com.wespot.staff.domain.vote.VoteRepository
import kotlinx.coroutines.launch

class QuestionAddViewModel(
    private val repository: VoteRepository,
): BaseViewModel<QuestionAddUiState, QuestionAddSideEffect>() {
    override fun createInitialState(): QuestionAddUiState = QuestionAddUiState()

    fun setQuestionList(questionList: List<VoteQuestionContent>) {
        reduce {
            copy(questionList = questionList)
        }
    }

    fun setQuestionItem(index: Int, question: String) {
        runCatching {
            reduce {
                val updatedList = state.questionList.toMutableList().apply {
                    if (index in indices) {
                        this[index] = question
                    }
                }
                copy(questionList = updatedList)
            }
        }
    }

    fun addQuestionItem() {
        reduce {
            copy(questionList = state.questionList + VoteQuestionContent())
        }
    }

    fun deleteQuestionItem(index: Int) {
        runCatching {
            reduce {
                val updatedList = state.questionList.toMutableList()
                updatedList.removeAt(index)
                copy(questionList = updatedList)
            }
        }
    }

    fun submitQuestionList() {
        viewModelScope.launch {
            val questionList = state.questionList
                .filter { it.isNotBlank() }
                .map { it.trim() }

            if (questionList.isEmpty()) {
                postSideEffect(QuestionAddSideEffect.ShowToast("질문 목록이 비어있어요."))
                return@launch
            }

            reduce { copy(isLoading = true) }
            repository.postVoteQuestions(questionList)
                .onSuccess {
                    clearState()
                    postSideEffect(QuestionAddSideEffect.NavigateToQuestionScreen)
                }
                .onFailure { exception ->
                    postSideEffect(QuestionAddSideEffect.ShowToast("${exception.message} 문제가 발생했어요."))
                }
                .also {
                    reduce { copy(isLoading = false) }
                }
        }
    }

    private fun clearState() {
        reduce {
            copy(questionList = listOf())
        }
    }
}
