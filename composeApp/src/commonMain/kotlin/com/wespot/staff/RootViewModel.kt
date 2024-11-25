package com.wespot.staff

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.config.RemoteConfigRepository
import kotlinx.coroutines.launch

class RootViewModel(
    private val remoteConfigRepository: RemoteConfigRepository,
): ViewModel() {
    init {
        startRemoteConfig()
    }

    private fun startRemoteConfig() {
        viewModelScope.launch {
            remoteConfigRepository.startRemoteConfig()
        }
    }
}
