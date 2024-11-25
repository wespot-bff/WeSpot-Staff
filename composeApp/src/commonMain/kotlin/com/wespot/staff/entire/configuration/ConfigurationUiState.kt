package com.wespot.staff.entire.configuration

import com.wespot.staff.domain.config.RemoteConfig

data class ConfigurationUiState(
    val remoteConfigList: List<RemoteConfig> = listOf(),
)
