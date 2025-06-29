package com.wespot.staff.data.notification.model

import com.wespot.staff.domain.notification.NotificationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationTypeDto(
    @SerialName("possibleToPublishEvent") val name: String,
) {
    fun toDomain(): NotificationType = NotificationType(name = name)
}
