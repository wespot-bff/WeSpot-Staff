package com.wespot.staff.entire.configuration

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.config.RemoteConfig
import com.wespot.staff.domain.config.RemoteConfigRepository
import kotlinx.coroutines.launch

class ConfigurationViewModel(
    private val remoteConfigRepository: RemoteConfigRepository,
): BaseViewModel<ConfigurationUiState, ConfigurationSideEffect>() {
    override fun createInitialState(): ConfigurationUiState {
        return ConfigurationUiState()
    }

    init {
        getRemoteConfig()
    }

    private fun getRemoteConfig() {
        viewModelScope.launch {
            reduce { state.copy(isLoading = true) }
            remoteConfigRepository.getRemoteConfigValue()
                .onSuccess {
                    reduce { copy(remoteConfigList = it) }
                }.onFailure {
                    postSideEffect(ConfigurationSideEffect.ShowErrorToast("값을 불러오는데 실패하였습니다."))
                }.also {
                    reduce { state.copy(isLoading = false) }
                }
        }
    }

    fun selectRemoteConfig(remoteConfig: RemoteConfig) {
        reduce {
            state.copy(selectedRemoteConfig = remoteConfig)
        }
        postSideEffect(ConfigurationSideEffect.ShowBottomSheet)
    }

    fun handleDeleteBottomSheetClicked() {
        viewModelScope.launch {
            reduce { state.copy(isLoading = true) }
            remoteConfigRepository.deleteRemoteConfigValue(state.selectedRemoteConfig.key)
                .onSuccess {
                    getRemoteConfig()
                    postSideEffect(ConfigurationSideEffect.ShowToast("삭제 완료"))
                }
                .onFailure {
                    postSideEffect(ConfigurationSideEffect.ShowErrorToast("알 수 없는 에러가 발생했습니다. ${it.message.toString()}"))
                }.also {
                    reduce { state.copy(isLoading = false) }
                }
        }
    }

    fun handleEditBottomSheetClicked() {
        postSideEffect(ConfigurationSideEffect.ShowEditConfigurationDialog)
    }

    fun setRemoteConfigValue(value: String) {
        reduce {
            state.copy(selectedRemoteConfig = state.selectedRemoteConfig.copy(value = value))
        }
    }

    fun editRemoteConfig() {
        reduce { state.copy(isLoading = true) }
        viewModelScope.launch {
            remoteConfigRepository.putRemoteConfigValue(remoteConfig = state.selectedRemoteConfig)
                .onSuccess {
                    postSideEffect(ConfigurationSideEffect.ShowToast("수정 완료"))
                    getRemoteConfig()
                }.onFailure {
                    postSideEffect(ConfigurationSideEffect.ShowErrorToast("알 수 없는 에러가 발생했습니다. ${it.message.toString()}"))
                }.also {
                    reduce { state.copy(isLoading = false) }
                }
        }
    }
}
