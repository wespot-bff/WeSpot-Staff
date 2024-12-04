package com.wespot.staff.vote.question

import com.arkivanov.decompose.ComponentContext

class QuestionComponent(
    componentContext: ComponentContext,
    val toastMessage: String? = null,
    private val popBackStack: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()
}
