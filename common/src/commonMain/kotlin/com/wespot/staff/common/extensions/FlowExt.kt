package com.wespot.staff.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectEvent(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    event: (suspend (sideEffect: T) -> Unit)
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val callback by rememberUpdatedState(newValue = event)

    LaunchedEffect(this, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            this@collectEvent.collect { callback(it) }
        }
    }
}
