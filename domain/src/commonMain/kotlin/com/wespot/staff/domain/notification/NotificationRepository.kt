package com.wespot.staff.domain.notification

interface NotificationRepository {
    suspend fun publishNotification(content: NotificationContent): Result<Unit>
}
