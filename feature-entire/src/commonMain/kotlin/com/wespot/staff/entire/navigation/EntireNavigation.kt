package com.wespot.staff.entire.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.wespot.staff.entire.configuration.ConfigurationScreen
import com.wespot.staff.entire.configuration.add.ConfigurationAddScreen
import com.wespot.staff.entire.home.EntireHomeScreen
import com.wespot.staff.entire.navigation.EntireRootComponent.EntireChild
import com.wespot.staff.entire.notification.NotificationScreen

@Composable
fun EntireNavigation(component: EntireRootComponent) {
    val childStack by component.stack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is EntireChild.EntireHomeScreen -> EntireHomeScreen(child.component)
            is EntireChild.ConfigurationScreen -> ConfigurationScreen(child.component)
            is EntireChild.ConfigurationAddScreen -> ConfigurationAddScreen(child.component)
            is EntireChild.NotificationScreen -> NotificationScreen(child.component)
        }
    }
}
