package com.wespot.staff.vote.write.add

sealed interface QuestionAddSideEffect {
    data object NavigateToQuestionScreen : QuestionAddSideEffect
    data class ShowSnackbar(val message: String): QuestionAddSideEffect
}
