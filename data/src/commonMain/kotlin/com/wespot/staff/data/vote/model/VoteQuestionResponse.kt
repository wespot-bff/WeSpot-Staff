package com.wespot.staff.data.vote.model

import com.wespot.staff.domain.vote.VoteQuestion
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class VoteQuestionResponse(
    val id: Long,
    val content: String,
    val createdAt: String,
    val updatedAt: String?,
) {
    fun toVoteQuestion(): VoteQuestion = VoteQuestion(
        id = id,
        content = content,
        createdAt = LocalDateTime.parse(createdAt),
        updatedAt = updatedAt?.let { LocalDateTime.parse(it) },
    )
}

@Serializable
data class VoteQuestionsResponse(
    val voteOptions: List<VoteQuestionResponse>
)
