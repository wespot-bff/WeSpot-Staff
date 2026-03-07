package com.wespot.staff.user.home

import androidx.lifecycle.viewModelScope
import com.wespot.staff.common.base.BaseViewModel
import com.wespot.staff.domain.user.User
import com.wespot.staff.domain.user.UserRepository
import kotlinx.coroutines.launch

class UserHomeViewModel(
    private val userRepository: UserRepository,
): BaseViewModel<UserHomeUiState, UserHomeSideEffect>() {
    override fun createInitialState(): UserHomeUiState = UserHomeUiState()

    init {
        getUsers()
    }

    private fun getUsers() {
        viewModelScope.launch {
            reduce { copy(isLoading = true) }
            userRepository.getUsers()
                .onSuccess { userList ->
                    reduce { copy(users = userList) }
                }
                .onFailure {
                    postSideEffect(UserHomeSideEffect.UserListLoadFailed)
                }.also {
                    reduce { copy(isLoading = false) }
                }
        }
    }

    fun onUserClicked(user: User) {
        reduce { copy(selectedUser = user, showBottomSheet = true) }
    }

    fun onDismissBottomSheet() {
        reduce { copy(showBottomSheet = false) }
    }

    fun deleteUser() {
        val user = state.selectedUser ?: return
        viewModelScope.launch {
            reduce { copy(isLoading = true, showBottomSheet = false) }
            userRepository.deleteUser(user.id)
                .onSuccess {
                    postSideEffect(UserHomeSideEffect.UserDeleteSuccess)
                    getUsers()
                }
                .onFailure {
                    postSideEffect(UserHomeSideEffect.UserDeleteFailed)
                }.also {
                    reduce { copy(isLoading = false) }
                }
        }
    }
}
