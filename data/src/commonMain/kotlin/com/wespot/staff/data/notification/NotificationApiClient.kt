package com.wespot.staff.data.notification

import com.wespot.staff.data.core.safeRequest
import com.wespot.staff.data.notification.model.NotificationContentDto
import com.wespot.staff.data.notification.model.NotificationTypeDto
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

interface NotificationApiClient {
    suspend fun publishNotification(content: NotificationContentDto): Result<Unit>

    suspend fun getNotificationType(): Result<List<NotificationTypeDto>>
}

class DefaultNotificationApiClient(
    private val httpClient: HttpClient,
): NotificationApiClient {
    override suspend fun publishNotification(content: NotificationContentDto): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/push-notification/publish")
                setBody(content)
            }
            method = HttpMethod.Post
        }

    override suspend fun getNotificationType(): Result<List<NotificationTypeDto>> =
        httpClient.safeRequest {
            url {
                path("/admin/push-notification/publish")
            }
            method = HttpMethod.Get
        }
}
