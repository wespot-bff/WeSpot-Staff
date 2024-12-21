package com.wespot.staff.entire.configuration.add

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.config.RemoteConfig
import com.wespot.staff.domain.config.RemoteConfigRepository
import kotlinx.coroutines.launch

class ConfigurationAddViewModel(
    private val remoteConfigRepository: RemoteConfigRepository
): BaseViewModel<ConfigurationAddUiState, ConfigurationAddSideEffect>() {
    override fun createInitialState(): ConfigurationAddUiState {
        return ConfigurationAddUiState()
    }

    fun setRemoteConfigKey(key: String) {
        reduce {
            copy(remoteConfigKey = key)
        }
    }

    fun setRemoteConfigValue(value: String) {
        reduce {
            copy(remoteConfigValue = value)
        }
    }

    fun postRemoteConfig() {
        reduce { state.copy(isLoading = true) }
        val key = state.remoteConfigKey.trim().replace(" ", "_")
        viewModelScope.launch {
            remoteConfigRepository.postRemoteConfigValue(remoteConfig = RemoteConfig(key = key, value = state.remoteConfigValue))
                .onSuccess {
                    postSideEffect(ConfigurationAddSideEffect.NavigateToHomeScreen)
                }.onFailure {
                    postSideEffect(ConfigurationAddSideEffect.ShowErrorToast("알 수 없는 에러가 발생했습니다. ${it.message.toString()}"))
                }.also {
                    reduce { state.copy(isLoading = false) }
                }
        }
    }
}
