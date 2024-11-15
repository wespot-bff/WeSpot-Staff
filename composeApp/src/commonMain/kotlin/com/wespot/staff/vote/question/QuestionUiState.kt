package com.wespot.staff.vote.question

import com.wespot.staff.domain.vote.VoteQuestion

data class QuestionUiState (
    val questionInput: String = "",
    val questionList: List<VoteQuestion> = listOf(),
    val clickedQuestion: VoteQuestion = VoteQuestion(),
    val searchInput: String = "",
    val isSearchState: Boolean = false,
)
