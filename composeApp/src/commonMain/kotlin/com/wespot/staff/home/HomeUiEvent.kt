package com.wespot.staff.home

sealed class HomeUiEvent {
    data object QuestionLoadFailedEvent : HomeUiEvent()
    data class QuestionPostEvent(val message: String) : HomeUiEvent()
}
