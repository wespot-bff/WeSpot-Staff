package com.wespot.staff.entire.configuration

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.config.RemoteConfigRepository
import kotlinx.coroutines.launch

class ConfigurationViewModel(
    private val remoteConfigRepository: RemoteConfigRepository,
): BaseViewModel<ConfigurationUiState, ConfigurationSideEffect>() {
    override fun createInitialState(): ConfigurationUiState {
        return ConfigurationUiState()
    }

    init {
        fetchRemoteConfig()
    }

    private fun fetchRemoteConfig() {
        viewModelScope.launch {
            reduce {
                copy(remoteConfigList = remoteConfigRepository.fetchFromRemoteConfig())
            }
        }
    }
}
