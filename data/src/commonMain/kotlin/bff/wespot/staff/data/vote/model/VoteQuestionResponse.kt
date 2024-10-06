package bff.wespot.staff.data.vote.model

import bff.wespot.staff.data.core.toLocalDateFromDashPattern
import bff.wespot.staff.domain.vote.VoteQuestion
import kotlinx.serialization.Serializable

@Serializable
data class VoteQuestionResponse (
    val id: Long,
    val content: String,
    val createdAt: String,
    val updateAt: String?,
) {
    fun toVoteQuestion(): VoteQuestion = VoteQuestion(
        id = id,
        content = content,
        createdAt = createdAt.toLocalDateFromDashPattern(),
        updateAt = updateAt?.toLocalDateFromDashPattern(),
    )
}
