package com.wespot.staff.entire.notification

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.notification.NotificationContent
import com.wespot.staff.domain.notification.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository,
): BaseViewModel<NotificationUiState, NotificationSideEffect>() {
    override fun createInitialState(): NotificationUiState = NotificationUiState()

    fun setTitle(title: String) {
        reduce {
            copy(title = title)
        }
    }

    fun setBody(body: String) {
        reduce {
            copy(body = body)
        }
    }

    fun publishNotification() {
        viewModelScope.launch {
            if (state.title.isBlank() || state.body.isBlank()) {
                postSideEffect(NotificationSideEffect.ShowSnackbar("제목과 내용을 모두 작성해주세요."))
            }

            reduce {
                copy(isLoading = true)
            }
            repository.publishNotification(
                content = NotificationContent(
                    title = state.title,
                    body = state.body,
                )
            ).onSuccess {
                postSideEffect(NotificationSideEffect.ShowSnackbar("알림 생성 완료"))
                postSideEffect(NotificationSideEffect.NavigateToHome)
            }.onFailure { exception ->
                postSideEffect(NotificationSideEffect.ShowSnackbar("${exception.message} 문제가 발생했어요."))
            }.also {
                reduce {
                    copy(isLoading = false)
                }
            }
        }
    }
}
