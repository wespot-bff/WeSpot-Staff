package com.wespot.staff.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.wespot.staff.home.navigation.HomeComponent
import com.wespot.staff.navigation.RootComponent.RootChild
import com.wespot.staff.report.navigation.ReportComponent
import com.wespot.staff.vote.navigation.VoteComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfiguration>()

    override val initialConfiguration: RootConfiguration = RootConfiguration.Home

    override val stack: Value<ChildStack<*, RootChild>> =
        childStack(
            source = navigation,
            serializer = RootConfiguration.serializer(),
            initialConfiguration = initialConfiguration,
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::createChild,
        )

    private fun createChild(config: RootConfiguration, componentContext: ComponentContext): RootChild =
        when (config) {
            is RootConfiguration.Home -> RootChild.HomeRoot(homeComponent(componentContext))
            is RootConfiguration.Vote -> RootChild.VoteRoot(voteComponent(componentContext))
            is RootConfiguration.Report -> RootChild.ReportRoot(reportComponent(componentContext))
        }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        HomeComponent(componentContext = componentContext)

    private fun voteComponent(componentContext: ComponentContext): VoteComponent =
        VoteComponent(componentContext = componentContext)

    private fun reportComponent(componentContext: ComponentContext): ReportComponent =
        ReportComponent(componentContext = componentContext)

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    override fun navigateRoot(configuration: RootConfiguration) {
        navigation.replaceAll(configuration)
    }
}
