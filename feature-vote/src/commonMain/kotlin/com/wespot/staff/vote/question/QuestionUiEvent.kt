package com.wespot.staff.vote.question

sealed class QuestionUiEvent {
    data object QuestionLoadFailedEvent : QuestionUiEvent()
    data class QuestionPostEvent(val message: String) : QuestionUiEvent()
}
