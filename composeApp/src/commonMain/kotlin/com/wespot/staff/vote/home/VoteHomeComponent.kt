package com.wespot.staff.vote.home

import com.arkivanov.decompose.ComponentContext

class VoteHomeComponent(
    componentContext: ComponentContext,
    private val navigateToQuestion: () -> Unit,
    private val navigateToQuestionWrite: () -> Unit,
): ComponentContext by componentContext {
    fun navigateToQuestionScreen() = navigateToQuestion()

    fun navigateToQuestionWriteScreen() = navigateToQuestionWrite()
}
