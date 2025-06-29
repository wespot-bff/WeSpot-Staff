package com.wespot.staff.entire.notification

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.notification.NotificationContent
import com.wespot.staff.domain.notification.NotificationRepository
import com.wespot.staff.domain.notification.NotificationType
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val repository: NotificationRepository,
): BaseViewModel<NotificationUiState, NotificationSideEffect>() {
    override fun createInitialState(): NotificationUiState = NotificationUiState()

    init {
        getNotificationTypes()
    }

    private fun getNotificationTypes() {
        viewModelScope.launch {
            repository.getNotificationType()
                .onSuccess {
                    reduce {
                        copy(
                            selectableNotificationTypes = it,
                            selectedNotificationType = it.firstOrNull() ?: NotificationType()
                        )
                    }
                }
                .onFailure {
                    postSideEffect(NotificationSideEffect.ShowSnackbar("앗! 알림 타입을 가져오는데 실패했어요"))
                }
        }
    }

    fun handleNotificationTypeClicked() {
        postSideEffect(NotificationSideEffect.ShowBottomSheet)
    }

    fun dismissNotificationTypeBottomSheet() {
        postSideEffect(NotificationSideEffect.DismissBottomSheet)
    }

    fun selectNotificationType(type: NotificationType) {
        reduce {
            state.copy(selectedNotificationType = type)
        }
        postSideEffect(NotificationSideEffect.DismissBottomSheet)
    }

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
                postSideEffect(NotificationSideEffect.ShowSnackbar("제목과 내용을 모두 작성해주세요"))
            }

            if (state.selectableNotificationTypes.isEmpty()) {
                postSideEffect(NotificationSideEffect.ShowSnackbar("알림 타입을 선택하세요"))
            }

            reduce {
                copy(isLoading = true)
            }
            repository.publishNotification(
                content = NotificationContent(
                    title = state.title,
                    body = state.body,
                    type = state.selectedNotificationType,
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
