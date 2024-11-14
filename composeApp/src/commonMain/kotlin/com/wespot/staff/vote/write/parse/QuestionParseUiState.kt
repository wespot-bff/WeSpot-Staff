package com.wespot.staff.vote.write.parse

import com.wespot.staff.domain.vote.VoteQuestionContent

data class QuestionParseUiState (
    val questionListString: String = "",
    val questionList: MutableList<VoteQuestionContent> = mutableListOf(),
)
