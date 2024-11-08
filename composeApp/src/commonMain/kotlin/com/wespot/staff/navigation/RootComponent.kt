package com.wespot.staff.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.wespot.staff.home.navigation.HomeComponent
import com.wespot.staff.report.navigation.ReportComponent
import com.wespot.staff.vote.navigation.VoteComponent

interface RootComponent {
    val initialConfiguration: RootConfiguration
    val stack: Value<ChildStack<*, RootChild>>

    // It's possible to pop multiple screens at a time on iOS
    fun onBackClicked(toIndex: Int)

    fun navigateRoot(configuration: RootConfiguration)

    sealed class RootChild {
        class HomeRoot(val component: HomeComponent) : RootChild()
        class VoteRoot(val component: VoteComponent) : RootChild()
        class ReportRoot(val component: ReportComponent) : RootChild()
    }
}
