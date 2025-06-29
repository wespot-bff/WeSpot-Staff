package com.wespot.staff.data.notification

import com.wespot.staff.data.notification.model.toNotificationContentDto
import com.wespot.staff.domain.notification.NotificationContent
import com.wespot.staff.domain.notification.NotificationRepository
import com.wespot.staff.domain.notification.NotificationType

class DefaultNotificationRepository(
    private val notificationApi: NotificationApiClient,
): NotificationRepository {
    override suspend fun publishNotification(content: NotificationContent): Result<Unit> =
        notificationApi.publishNotification(content.toNotificationContentDto())

    override suspend fun getNotificationType(): Result<List<NotificationType>> =
        notificationApi.getNotificationType().mapCatching { notificationTypeList ->
            notificationTypeList.map {
                it.toDomain()
            }
        }
}
