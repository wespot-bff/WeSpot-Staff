package com.wespot.staff.vote.write

import com.wespot.staff.domain.vote.VoteQuestionContent

data class QuestionWriteUiState (
    // add
    val questionList: MutableList<VoteQuestionContent> = mutableListOf(),

    // parse[
    val questionListString: String = "",
)