package com.wespot.staff

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.wespot.staff.common.extensions.collectSideEffect
import com.wespot.staff.designsystem.theme.WeSpotTheme
import com.wespot.staff.designsystem.util.snackbar.LocalSnackbarHost
import com.wespot.staff.designsystem.util.snackbar.SnackbarHost
import com.wespot.staff.navigation.RootComponent
import com.wespot.staff.navigation.RootComponent.RootChild
import com.wespot.staff.entire.navigation.EntireNavigation
import com.wespot.staff.report.ReportScreen
import com.wespot.staff.state.RootSideEffect
import com.wespot.staff.state.RootViewModel
import com.wespot.staff.vote.navigation.VoteNavigation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WeSpotStaffApp(
    component: RootComponent,
    viewModel: RootViewModel = koinViewModel(),
) {
    val childStack by component.stack.subscribeAsState()

    WeSpotTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val snackbarHost = object: SnackbarHost {
            override fun showSnackbar(message: String) {
                viewModel.handleShowSnackbarEvent(message)
            }
        }

        viewModel.sideEffect.collectSideEffect {
            when (it) {
                is RootSideEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }

        CompositionLocalProvider(
            values = arrayOf(
                LocalSnackbarHost provides snackbarHost
            ),
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    BottomNavigationBar(component = component, child = childStack.active.instance)
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    AppNavigation(childStack)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(component: RootComponent, child: RootChild) {
    var currentSelectedItem by remember { mutableStateOf(component.initialConfiguration) }

    AnimatedContent(
        targetState = child,
        transitionSpec = {
            fadeIn(animationSpec = tween()) togetherWith fadeOut(animationSpec = tween())
        },
        label = "Bottom Navigation Bar"
    ) { targetChild ->
        val isBottomBarVisible = when (targetChild) {
            is RootChild.VoteRoot -> {
                val voteChild by targetChild.component.stack.subscribeAsState()
                targetChild.component.isBottomBarImpression(voteChild.active.instance)
            }
            is RootChild.ReportRoot -> true
            is RootChild.EntireRoot -> {
                val entireChild by targetChild.component.stack.subscribeAsState()
                targetChild.component.isBottomBarImpression(entireChild.active.instance)
            }
        }

        if (isBottomBarVisible) {
            BottomNavigationTab(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    currentSelectedItem = selected
                    component.navigateRoot(selected)
                },
                modifier = Modifier.fillMaxWidth(),
            )
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
            is RootChild.VoteRoot -> VoteNavigation(component = child.component)
            is RootChild.ReportRoot -> ReportScreen(component = child.component)
            is RootChild.EntireRoot -> EntireNavigation(component = child.component)
        }
    }
}
