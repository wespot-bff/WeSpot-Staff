package com.wespot.staff.vote

import com.arkivanov.decompose.ComponentContext

class QuestionWriteComponent(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()
}
