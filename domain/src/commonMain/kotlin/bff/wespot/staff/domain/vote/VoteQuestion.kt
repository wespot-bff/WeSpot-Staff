package bff.wespot.staff.domain.vote

import kotlinx.datetime.LocalDate

data class VoteQuestion(
    val id: Long,
    val content: String,
    val createdAt: LocalDate,
    val updateAt: LocalDate?,
) {
    constructor(): this(-1, "", LocalDate(2001, 5, 8), null)

    fun toTimeDescription(): String =
        updateAt?.let { "마지막 질문 수정 시간 $it" } ?: "질문 생성 시간 $createdAt"
}
