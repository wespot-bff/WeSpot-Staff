package com.wespot.staff.user.home

sealed class UserHomeSideEffect {
    data object UserListLoadFailed : UserHomeSideEffect()
    data object UserDeleteSuccess : UserHomeSideEffect()
    data object UserDeleteFailed : UserHomeSideEffect()
}
