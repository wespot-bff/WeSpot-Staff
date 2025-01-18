package com.wespot.staff.entire.configuration

import com.arkivanov.decompose.ComponentContext

class ConfigurationComponent(
    componentContext: ComponentContext,
    private val popBackStack: () -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()
}
