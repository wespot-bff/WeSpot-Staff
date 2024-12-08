package com.wespot.staff.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, SideEffect> : ViewModel() {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    private val _sideEffect = MutableSharedFlow<SideEffect>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val sideEffect = _sideEffect.asSharedFlow()

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    val state get() = uiState.value

    protected fun reduce(action: State.() -> State) {
        _uiState.update(action)
    }

    protected suspend fun postSideEffect(sideEffect: SideEffect) {
        _sideEffect.emit(sideEffect)
    }
}