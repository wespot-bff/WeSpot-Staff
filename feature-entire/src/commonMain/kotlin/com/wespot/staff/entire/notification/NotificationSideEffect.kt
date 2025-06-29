package com.wespot.staff.entire.notification

sealed interface NotificationSideEffect {
    data class ShowSnackbar(val message: String): NotificationSideEffect
    data object NavigateToHome : NotificationSideEffect
    data object ShowBottomSheet : NotificationSideEffect
    data object DismissBottomSheet : NotificationSideEffect
}
