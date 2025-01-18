package com.wespot.staff.designsystem.util

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState>{
    error("No Snackbar Host State")
}
