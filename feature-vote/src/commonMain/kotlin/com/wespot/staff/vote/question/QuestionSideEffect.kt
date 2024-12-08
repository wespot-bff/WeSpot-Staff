package com.wespot.staff.vote.question

sealed class QuestionSideEffect {
    data object QuestionLoadFailedEvent : QuestionSideEffect()
    data class QuestionPostEvent(val message: String) : QuestionSideEffect()
}
