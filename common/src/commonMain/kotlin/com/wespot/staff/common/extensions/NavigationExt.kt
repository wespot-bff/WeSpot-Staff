package com.wespot.staff.common.extensions

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.pushToFront

fun <T : Any> StackNavigation<T>.navigate(configuration: T) {
    runCatching {
        this.pushNew(configuration)
    }.onFailure {
        this.pushToFront(configuration)
    }
}
