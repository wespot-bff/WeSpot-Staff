package com.wespot.staff.entire.notification

import com.wespot.staff.domain.notification.NotificationType

data class NotificationUiState(
    val title: String = "",
    val body: String = "",
    val selectableNotificationTypes: List<NotificationType> = listOf(),
    val selectedNotificationType: NotificationType = NotificationType(),
    val isLoading: Boolean = false,
)
