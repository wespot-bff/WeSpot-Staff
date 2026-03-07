package com.wespot.staff.user.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.wespot.staff.user.home.UserHomeComponent
import com.wespot.staff.user.navigation.UserRootComponent.UserChild
import kotlinx.serialization.Serializable

interface UserRootComponent {
    val stack: Value<ChildStack<*, UserChild>>

    fun isBottomBarImpression(userChild: UserChild): Boolean

    sealed class UserChild {
        class UserHomeScreen(val component: UserHomeComponent) : UserChild()
    }
}

class DefaultUserRootComponent(
    componentContext: ComponentContext,
): UserRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<UserConfiguration>()

    override val stack: Value<ChildStack<*, UserChild>> =
        childStack(
            source = navigation,
            serializer = null,
            initialConfiguration = UserConfiguration.UserHome,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    override fun isBottomBarImpression(userChild: UserChild): Boolean =
        userChild is UserChild.UserHomeScreen

    private fun createChild(config: UserConfiguration, componentContext: ComponentContext): UserChild =
        when (config) {
            is UserConfiguration.UserHome -> UserChild.UserHomeScreen(userHomeComponent(componentContext))
        }

    private fun userHomeComponent(componentContext: ComponentContext) =
        UserHomeComponent(componentContext = componentContext)

    @Serializable
    sealed interface UserConfiguration {
        @Serializable
        data object UserHome : UserConfiguration
    }
}
