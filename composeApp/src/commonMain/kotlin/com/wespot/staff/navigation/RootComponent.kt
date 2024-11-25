package com.wespot.staff.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.wespot.staff.entire.navigation.EntireRootComponent
import com.wespot.staff.message.navigation.MessageComponent
import com.wespot.staff.vote.navigation.VoteRootComponent

interface RootComponent {
    val initialConfiguration: RootConfiguration
    val stack: Value<ChildStack<*, RootChild>>

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    fun navigateRoot(configuration: RootConfiguration)

    sealed class RootChild {
        class VoteRoot(val component: VoteRootComponent) : RootChild()
        class MessageRoot(val component: MessageComponent) : RootChild()
        class EntireRoot(val component: EntireRootComponent) : RootChild()
    }
}
