package com.wespot.staff.entire.configuration.add

data class ConfigurationAddUiState(
    val remoteConfigKey: String = "",
    val remoteConfigValue: String = "",
    val isLoading: Boolean = false,
)
