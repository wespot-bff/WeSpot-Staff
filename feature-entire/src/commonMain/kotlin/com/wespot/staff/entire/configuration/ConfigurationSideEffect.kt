package com.wespot.staff.entire.configuration

sealed interface ConfigurationSideEffect {
    data class ShowErrorToast(val message: String): ConfigurationSideEffect
    data class ShowToast(val message: String): ConfigurationSideEffect
    data object ShowEditConfigurationDialog: ConfigurationSideEffect
    data object ShowBottomSheet: ConfigurationSideEffect
}
