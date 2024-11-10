package com.wespot.staff.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.wespot.staff.vote.navigation.VoteComponent
import com.wespot.staff.report.navigation.ReportComponent
import com.wespot.staff.message.navigation.MessageComponent

interface RootComponent {
    val initialConfiguration: RootConfiguration
    val stack: Value<ChildStack<*, RootChild>>

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    fun navigateRoot(configuration: RootConfiguration)

    sealed class RootChild {
        class VoteRoot(val component: VoteComponent) : RootChild()
        class MessageRoot(val component: MessageComponent) : RootChild()
        class ReportRoot(val component: ReportComponent) : RootChild()
    }
}