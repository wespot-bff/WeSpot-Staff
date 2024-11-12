package com.wespot.staff

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.active
import com.wespot.staff.designsystem.theme.WeSpotTheme
import com.wespot.staff.message.MessageScreen
import com.wespot.staff.navigation.RootComponent
import com.wespot.staff.navigation.RootComponent.RootChild
import com.wespot.staff.report.ReportScreen
import com.wespot.staff.vote.navigation.VoteNavigation
import com.wespot.staff.vote.navigation.VoteRootComponent

@Composable
fun WeSpotStaffApp(component: RootComponent) {
    val childStack by component.stack.subscribeAsState()
    val child = childStack.active.instance

    WeSpotTheme {
        Scaffold(
            bottomBar = {
                AnimatedContent(
                    targetState = isBottomBannerVisible(child),
                    transitionSpec = {
                        fadeIn(animationSpec = tween()) togetherWith fadeOut(animationSpec = tween())
                    },
                    label = "Bottom Navigation Bar"
                ) {
                    val currentSelectedItem = remember { mutableStateOf(component.initialConfiguration) }
                    BottomNavigationTab(
                        selectedNavigation = currentSelectedItem.value,
                        onNavigationSelected = { selected ->
                            currentSelectedItem.value = selected
                            component.navigateRoot(selected)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                AppNavigation(childStack)
            }
        }
    }
}

@Composable
fun AppNavigation(childStack: ChildStack<*, RootChild>) {
    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootChild.VoteRoot -> VoteNavigation(child.component)
            is RootChild.MessageRoot -> MessageScreen(component = child.component)
            is RootChild.ReportRoot -> ReportScreen(component = child.component)
        }
    }
}

fun isBottomBannerVisible(child: RootChild): Boolean =
    when (child) {
        is RootChild.VoteRoot -> {
            val voteChild = child.component.stack.active.instance
            voteChild !is VoteRootComponent.VoteChild.QuestionScreen
        }
        is RootChild.MessageRoot, is RootChild.ReportRoot -> true
    }
