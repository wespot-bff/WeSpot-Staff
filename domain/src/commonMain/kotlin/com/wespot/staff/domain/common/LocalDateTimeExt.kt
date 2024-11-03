package com.wespot.staff.domain.common

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.toDescription(): String {
    val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year

    val formattedDate = if (currentYear == year) {
        "${monthNumber}월 ${dayOfMonth}일 ${hour}시 ${minute}분"
    } else {
        "${year}년 ${monthNumber}월 ${dayOfMonth}일 ${hour}시 ${minute}분"
    }

    return formattedDate
}
