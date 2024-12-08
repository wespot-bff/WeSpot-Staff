package com.wespot.staff.vote.write.add

sealed interface QuestionAddSideEffect {
    data object NavigateToQuestionScreen : QuestionAddSideEffect
    data class ShowToast(val message: String): QuestionAddSideEffect
}
