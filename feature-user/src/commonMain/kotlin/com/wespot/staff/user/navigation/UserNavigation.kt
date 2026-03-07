package com.wespot.staff.user.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.wespot.staff.user.home.UserHomeScreen
import com.wespot.staff.user.navigation.UserRootComponent.UserChild

@Composable
fun UserNavigation(component: UserRootComponent) {
    val childStack by component.stack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is UserChild.UserHomeScreen -> UserHomeScreen(child.component)
        }
    }
}
