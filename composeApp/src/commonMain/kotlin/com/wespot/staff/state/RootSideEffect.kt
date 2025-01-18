package com.wespot.staff.state

sealed interface RootSideEffect {
    data class ShowSnackbar(val message: String) : RootSideEffect
}
