package com.wespot.staff.vote.write.add

sealed interface QuestionAddUiEvent {
    data object NavigateToQuestionScreen : QuestionAddUiEvent
    data class ShowToast(val message: String): QuestionAddUiEvent
}
