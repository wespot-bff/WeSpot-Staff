package com.wespot.staff.entire.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wespot.staff.domain.notification.NotificationContent
import com.wespot.staff.domain.notification.NotificationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _uiState.value
        )

    private val _uiEvent = Channel<NotificationUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun setTitle(title: String) {
        _uiState.update {
            it.copy(title = title)
        }
    }

    fun setBody(body: String) {
        _uiState.update {
            it.copy(body = body)
        }
    }

    fun publishNotification() {
        viewModelScope.launch {
            if (_uiState.value.title.isBlank() || _uiState.value.body.isBlank()) {
                _uiEvent.send(NotificationUiEvent.ShowErrorMessage("제목과 내용을 모두 작성해주세요."))
            }

            repository.publishNotification(
                content = NotificationContent(
                    title = _uiState.value.title,
                    body = _uiState.value.body,
                )
            ).onSuccess {
                _uiEvent.send(NotificationUiEvent.NavigateToHome)
            }.onFailure { exception ->
                _uiEvent.send(NotificationUiEvent.ShowErrorMessage("${exception.message} 문제가 발생했어요."))
            }
        }
    }
}
