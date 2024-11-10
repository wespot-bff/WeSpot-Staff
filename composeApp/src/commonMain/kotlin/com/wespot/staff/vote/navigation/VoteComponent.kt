package com.wespot.staff.vote.navigation

import com.arkivanov.decompose.ComponentContext

class VoteComponent(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()

}
