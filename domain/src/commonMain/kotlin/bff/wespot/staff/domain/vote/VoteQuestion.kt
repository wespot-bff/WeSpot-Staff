package bff.wespot.staff.domain.vote

import bff.wespot.staff.domain.common.toDescription
import kotlinx.datetime.LocalDateTime

data class VoteQuestion(
    val id: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
) {
    constructor(): this(-1, "", LocalDateTime(2001, 5, 8, 0, 0), null)

    fun toTimeDescription(): String {
        return if (updatedAt != null) {
            "마지막 질문 수정 시간 ${updatedAt.toDescription()}"
        } else {
            "질문 생성 시간 ${createdAt.toDescription()}"
        }
    }
}
