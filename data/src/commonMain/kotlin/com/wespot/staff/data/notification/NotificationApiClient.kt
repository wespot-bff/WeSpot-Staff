package com.wespot.staff.data.notification

import com.wespot.staff.data.core.safeRequest
import com.wespot.staff.data.notification.model.NotificationContentDto
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

interface NotificationApiClient {
    suspend fun publishNotification(content: NotificationContentDto): Result<Unit>
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
}
