package com.wespot.staff.entire.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.wespot.staff.entire.configuration.ConfigurationComponent
import com.wespot.staff.entire.home.EntireHomeComponent
import com.wespot.staff.entire.navigation.EntireRootComponent.EntireChild
import com.wespot.staff.entire.notification.NotificationComponent
import kotlinx.serialization.Serializable

interface EntireRootComponent {
    val stack: Value<ChildStack<*, EntireChild>>

    fun isBottomBarImpression(entireChild: EntireChild): Boolean

    fun popBackStack()

    sealed class EntireChild {
        class EntireHomeScreen(val component: EntireHomeComponent) : EntireChild()
        class ConfigurationScreen(val component: ConfigurationComponent) : EntireChild()
        class NotificationScreen(val component: NotificationComponent) : EntireChild()
    }
}

class DefaultEntireRootComponent(
    componentContext: ComponentContext,
): EntireRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<EntireConfiguration>()

    override val stack: Value<ChildStack<*, EntireChild>> =
        childStack(
            source = navigation,
            serializer = EntireConfiguration.serializer(),
            initialConfiguration = EntireConfiguration.EntireHome,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    override fun isBottomBarImpression(entireChild: EntireChild): Boolean =
        entireChild is EntireChild.EntireHomeScreen

    override fun popBackStack() {
        navigation.pop()
    }

    private fun createChild(config: EntireConfiguration, componentContext: ComponentContext): EntireChild =
        when (config) {
            is EntireConfiguration.EntireHome -> EntireChild.EntireHomeScreen(entireHomeComponent(componentContext))
            is EntireConfiguration.Configuration -> EntireChild.ConfigurationScreen(configurationComponent(componentContext))
            is EntireConfiguration.Notification -> EntireChild.NotificationScreen(notificationComponent(componentContext))
        }

    private fun entireHomeComponent(componentContext: ComponentContext) =
        EntireHomeComponent(
            componentContext = componentContext,
            navigateToNotification = {
                navigation.pushToFront(EntireConfiguration.Notification)
            },
            navigateToConfiguration = {
                navigation.pushToFront(EntireConfiguration.Configuration)
            }
        )

    private fun configurationComponent(componentContext: ComponentContext) =
        ConfigurationComponent(
            componentContext = componentContext,
            popBackStack = ::popBackStack,
        )

    private fun notificationComponent(componentContext: ComponentContext) =
        NotificationComponent(
            componentContext = componentContext,
            popBackStack = ::popBackStack,
            navigateToHome = {
                navigation.pushToFront(EntireConfiguration.EntireHome)
            },
        )

    @Serializable
    sealed interface EntireConfiguration {
        @Serializable
        data object EntireHome : EntireConfiguration

        @Serializable
        data object Configuration : EntireConfiguration

        @Serializable
        data object Notification : EntireConfiguration
    }
}
