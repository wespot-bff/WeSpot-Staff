package com.wespot.staff.home

import bff.wespot.staff.domain.vote.VoteQuestion

data class HomeUiState (
    val questionInput: String = "",
    val questionList: List<VoteQuestion> = listOf(),
    val clickedQuestion: VoteQuestion = VoteQuestion(),
    val searchInput: String = "",
    val isSearchState: Boolean = false,
)
