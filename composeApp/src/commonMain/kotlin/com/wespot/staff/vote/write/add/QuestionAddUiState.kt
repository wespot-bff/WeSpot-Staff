package com.wespot.staff.vote.write.add

import com.wespot.staff.domain.vote.VoteQuestionContent

data class QuestionAddUiState(
    val questionList: List<VoteQuestionContent> = listOf(VoteQuestionContent()),
)
