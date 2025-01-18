package com.wespot.staff.data.notification.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationContentDto(
    val title: String,
    val body: String,
)
