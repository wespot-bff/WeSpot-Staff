package com.wespot.staff.entire.notification

sealed interface NotificationUiEvent {
    data class ShowErrorMessage(val message: String): NotificationUiEvent
    data object NavigateToHome : NotificationUiEvent
}
