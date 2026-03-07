package com.wespot.staff.user.home

import com.wespot.staff.domain.user.User

data class UserHomeUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val selectedUser: User? = null,
    val showBottomSheet: Boolean = false,
)
