package com.wespot.staff.data.vote.model

import com.wespot.staff.domain.vote.VoteQuestion

internal fun VoteQuestion.toVoteQuestionResponse(): VoteQuestionResponse =
    VoteQuestionResponse(
        id = id,
        content = content,
        createdAt = createdAt.toString(),
        updatedAt = updatedAt?.toString(),
    )
