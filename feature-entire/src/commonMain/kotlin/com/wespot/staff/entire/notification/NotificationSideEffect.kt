package com.wespot.staff.entire.notification

sealed interface NotificationSideEffect {
    data class ShowErrorMessage(val message: String): NotificationSideEffect
    data object NavigateToHome : NotificationSideEffect
}
