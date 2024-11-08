package com.wespot.staff

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.wespot.staff.designsystem.theme.WeSpotTheme
import com.wespot.staff.home.HomeScreen
import com.wespot.staff.navigation.RootComponent
import com.wespot.staff.navigation.RootComponent.RootChild
import com.wespot.staff.report.ReportScreen
import com.wespot.staff.vote.VoteScreen

@Composable
fun WeSpotStaffApp(component: RootComponent) {
    WeSpotTheme {
        val currentSelectedItem = remember { mutableStateOf(component.initialConfiguration) }

        Scaffold(
            bottomBar = {
                BottomNavigationTab(
                    selectedNavigation = currentSelectedItem.value,
                    onNavigationSelected = { selected ->
                        currentSelectedItem.value = selected
                        component.navigateRoot(selected)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        ) {
            Box(modifier = Modifier.padding(it)) {
                AppNavigation(component)
            }
        }
    }
}

@Composable
fun AppNavigation(component: RootComponent) {
    val childStack by component.stack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootChild.HomeRoot -> HomeScreen(component = child.component)
            is RootChild.VoteRoot -> VoteScreen(component = child.component)
            is RootChild.ReportRoot -> ReportScreen(component = child.component)
        }
    }
}
