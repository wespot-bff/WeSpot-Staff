package com.wespot.staff.entire.configuration.add


sealed interface ConfigurationAddSideEffect {
    data class ShowErrorToast(val message: String): ConfigurationAddSideEffect
    data object NavigateToHomeScreen: ConfigurationAddSideEffect
}
