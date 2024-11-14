package com.wespot.staff.vote.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.wespot.staff.vote.QuestionWriteComponent
import com.wespot.staff.vote.home.VoteHomeComponent
import com.wespot.staff.vote.navigation.VoteRootComponent.VoteChild
import com.wespot.staff.vote.question.QuestionComponent
import kotlinx.serialization.Serializable

interface VoteRootComponent {
    val stack: Value<ChildStack<*, VoteChild>>

    fun popBackStack()

    sealed class VoteChild {
        class VoteHomeScreen(val component: VoteHomeComponent) : VoteChild()
        class QuestionScreen(val component: QuestionComponent) : VoteChild()
        class QuestionWriteScreen(val component: QuestionWriteComponent) : VoteChild()
    }
}

class DefaultVoteRootComponent(
    componentContext: ComponentContext,
): VoteRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<VoteConfiguration>()

    override val stack: Value<ChildStack<*, VoteChild>> =
        childStack(
            source = navigation,
            serializer = VoteConfiguration.serializer(),
            initialConfiguration = VoteConfiguration.VoteHome,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    override fun popBackStack() {
        navigation.pop()
    }

    private fun createChild(config: VoteConfiguration, componentContext: ComponentContext): VoteChild =
        when (config) {
            is VoteConfiguration.VoteHome -> VoteChild.VoteHomeScreen(voteHomeComponent(componentContext))
            is VoteConfiguration.Question -> VoteChild.QuestionScreen(questionComponent(componentContext))
            is VoteConfiguration.QuestionWrite -> VoteChild.QuestionWriteScreen(questionWriteComponent(componentContext))
        }

    private fun voteHomeComponent(componentContext: ComponentContext): VoteHomeComponent =
        VoteHomeComponent(
            componentContext = componentContext,
            navigateToQuestion = {
                navigation.pushNew(VoteConfiguration.Question)
            },
            navigateToQuestionWrite = {
                navigation.pushNew(VoteConfiguration.QuestionWrite)
            }
        )

    private fun questionComponent(componentContext: ComponentContext): QuestionComponent =
        QuestionComponent(componentContext = componentContext, popBackStack = ::popBackStack)

    private fun questionWriteComponent(componentContext: ComponentContext): QuestionWriteComponent =
        QuestionWriteComponent(componentContext = componentContext, popBackStack = ::popBackStack)

    @Serializable
    sealed interface VoteConfiguration {
        @Serializable
        data object VoteHome : VoteConfiguration

        @Serializable
        data object Question : VoteConfiguration

        @Serializable
        data object QuestionWrite : VoteConfiguration
    }
}
