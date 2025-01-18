package com.wespot.staff.designsystem.util.snackbar

import androidx.compose.runtime.compositionLocalOf

val LocalSnackbarHost = compositionLocalOf<SnackbarHost>{
    NoSnackbarHost()
}
