package com.wespot.staff.entire.configuration.add

import com.arkivanov.decompose.ComponentContext

class ConfigurationAddComponent(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
    private val popUpToHome: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()

    fun navigateToHomeScreen() = popUpToHome()
}
