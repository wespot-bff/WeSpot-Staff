package com.wespot.staff.entire.home

import com.arkivanov.decompose.ComponentContext

class EntireHomeComponent(
    componentContext: ComponentContext,
    private val navigateToConfiguration: () -> Unit,
    private val navigateToNotification: () -> Unit,
    private val navigateToAddConfiguration: () -> Unit,
): ComponentContext by componentContext {
    fun navigateToConfigurationScreen() = navigateToConfiguration()

    fun navigateToAddConfigurationScreen() = navigateToAddConfiguration()

    fun navigateToNotificationScreen() = navigateToNotification()
}
