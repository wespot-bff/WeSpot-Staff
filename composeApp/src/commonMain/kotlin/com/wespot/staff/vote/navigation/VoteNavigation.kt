package com.wespot.staff.vote.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.wespot.staff.vote.home.VoteHomeScreen
import com.wespot.staff.vote.navigation.VoteRootComponent.VoteChild
import com.wespot.staff.vote.question.QuestionScreen
import com.wespot.staff.vote.write.QuestionWriteScreen

@Composable
fun VoteNavigation(component: VoteRootComponent) {
    val childStack by component.stack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is VoteChild.VoteHomeScreen -> VoteHomeScreen(child.component)
            is VoteChild.QuestionScreen -> QuestionScreen(child.component)
            is VoteChild.QuestionWriteScreen -> QuestionWriteScreen(child.component)
        }
    }
}

