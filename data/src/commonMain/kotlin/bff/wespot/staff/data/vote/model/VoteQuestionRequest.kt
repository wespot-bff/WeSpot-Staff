package bff.wespot.staff.data.vote.model

import kotlinx.serialization.Serializable

@Serializable
data class VoteQuestionRequest (
    val content: String,
)
