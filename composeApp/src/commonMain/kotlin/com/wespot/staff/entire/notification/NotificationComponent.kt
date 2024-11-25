package com.wespot.staff.entire.notification

import com.arkivanov.decompose.ComponentContext

class NotificationComponent(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
    private val navigateToHome: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()

    fun navigateToHomeScreen() = navigateToHome()
}