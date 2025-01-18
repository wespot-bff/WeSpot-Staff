package com.wespot.staff.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.popTo
import com.arkivanov.decompose.value.Value
import com.wespot.staff.entire.navigation.DefaultEntireRootComponent
import com.wespot.staff.navigation.RootComponent.RootChild
import com.wespot.staff.entire.navigation.EntireRootComponent
import com.wespot.staff.report.navigation.ReportComponent
import com.wespot.staff.vote.navigation.DefaultVoteRootComponent
import com.wespot.staff.vote.navigation.VoteRootComponent

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<RootConfiguration>()

    override val initialConfiguration: RootConfiguration = RootConfiguration.Vote

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
            is RootConfiguration.Vote -> RootChild.VoteRoot(voteComponent(componentContext))
            is RootConfiguration.Message -> RootChild.ReportRoot(messageComponent(componentContext))
            is RootConfiguration.Entire -> RootChild.EntireRoot(entireComponent(componentContext))
        }

    private fun voteComponent(componentContext: ComponentContext): VoteRootComponent =
        DefaultVoteRootComponent(componentContext = componentContext)

    private fun messageComponent(componentContext: ComponentContext): ReportComponent =
        ReportComponent(componentContext = componentContext)

    private fun entireComponent(componentContext: ComponentContext): EntireRootComponent =
        DefaultEntireRootComponent(componentContext = componentContext)

    override fun onBackClicked(toIndex: Int) {
        navigation.popTo(index = toIndex)
    }

    override fun navigateRoot(configuration: RootConfiguration) {
        navigation.bringToFront(configuration)
    }
}
