package com.wespot.staff.data.notification

import com.wespot.staff.data.notification.model.toNotificationContentDto
import com.wespot.staff.domain.notification.NotificationContent
import com.wespot.staff.domain.notification.NotificationRepository

class DefaultNotificationRepository(
    private val notificationApi: NotificationApiClient,
): NotificationRepository {
    override suspend fun publishNotification(content: NotificationContent): Result<Unit> =
        notificationApi.publishNotification(content.toNotificationContentDto())
}
