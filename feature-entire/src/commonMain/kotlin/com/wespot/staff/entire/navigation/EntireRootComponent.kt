package com.wespot.staff.entire.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.wespot.staff.common.extensions.navigate
import com.wespot.staff.entire.configuration.ConfigurationComponent
import com.wespot.staff.entire.configuration.add.ConfigurationAddComponent
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
        class ConfigurationAddScreen(val component: ConfigurationAddComponent) : EntireChild()
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
            initialConfiguration = EntireConfiguration.EntireHome(),
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
            is EntireConfiguration.EntireHome -> EntireChild.EntireHomeScreen(entireHomeComponent(componentContext, config))
            is EntireConfiguration.Configuration -> EntireChild.ConfigurationScreen(configurationComponent(componentContext))
            is EntireConfiguration.Notification -> EntireChild.NotificationScreen(notificationComponent(componentContext))
            is EntireConfiguration.ConfigurationAdd -> EntireChild.ConfigurationAddScreen(configurationAddComponent(componentContext))
        }

    private fun entireHomeComponent(componentContext: ComponentContext, config: EntireConfiguration.EntireHome) =
        EntireHomeComponent(
            componentContext = componentContext,
            toastMessage = config.toastMessage,
            navigateToNotification = {
                navigation.navigate(EntireConfiguration.Notification)
            },
            navigateToConfiguration = {
                navigation.navigate(EntireConfiguration.Configuration)
            },
            navigateToAddConfiguration = {
                navigation.navigate(EntireConfiguration.ConfigurationAdd)
            },
        )

    private fun configurationComponent(componentContext: ComponentContext) =
        ConfigurationComponent(
            componentContext = componentContext,
            popBackStack = ::popBackStack,
        )

    private fun configurationAddComponent(componentContext: ComponentContext) =
        ConfigurationAddComponent(
            componentContext = componentContext,
            popBackStack = ::popBackStack,
            navigateToHome = {
                navigation.navigate(EntireConfiguration.EntireHome(it))
            },
        )

    private fun notificationComponent(componentContext: ComponentContext) =
        NotificationComponent(
            componentContext = componentContext,
            popBackStack = ::popBackStack,
            navigateToHome = {
                navigation.navigate(EntireConfiguration.EntireHome(it))
            },
        )

    @Serializable
    sealed interface EntireConfiguration {
        @Serializable
        data class EntireHome(val toastMessage: String? = null) : EntireConfiguration

        @Serializable
        data object Configuration : EntireConfiguration

        @Serializable
        data object Notification : EntireConfiguration

        @Serializable
        data object ConfigurationAdd : EntireConfiguration
    }
}
