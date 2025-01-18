package com.wespot.staff.state

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.config.RemoteConfigRepository
import kotlinx.coroutines.launch

class RootViewModel(
    private val remoteConfigRepository: RemoteConfigRepository,
): BaseViewModel<RootUiState, RootSideEffect>() {
    override fun createInitialState(): RootUiState = RootUiState()

    init {
        startRemoteConfig()
    }

    private fun startRemoteConfig() {
        viewModelScope.launch {
            remoteConfigRepository.startRemoteConfig()
        }
    }

    fun handleShowSnackbarEvent(message: String) {
        postSideEffect(RootSideEffect.ShowSnackbar(message))
    }
}
