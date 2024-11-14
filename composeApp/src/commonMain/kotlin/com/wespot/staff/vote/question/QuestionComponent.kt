package com.wespot.staff.vote.question

import com.arkivanov.decompose.ComponentContext

class QuestionComponent(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack
}
