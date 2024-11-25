package com.wespot.staff.entire.configuration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.config.RemoteConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfigurationViewModel(
    private val remoteConfigRepository: RemoteConfigRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(ConfigurationUiState())
    val uiState = _uiState
        .onStart {
            fetchRemoteConfig()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    private fun fetchRemoteConfig() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(remoteConfigList = remoteConfigRepository.fetchFromRemoteConfig())
            }
        }
    }
}
