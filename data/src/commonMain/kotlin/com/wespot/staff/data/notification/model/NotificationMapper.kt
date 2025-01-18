package com.wespot.staff.data.notification.model

import com.wespot.staff.domain.notification.NotificationContent

internal fun NotificationContent.toNotificationContentDto() =
    NotificationContentDto(
        title = this.title,
        body = this.body,
    )
