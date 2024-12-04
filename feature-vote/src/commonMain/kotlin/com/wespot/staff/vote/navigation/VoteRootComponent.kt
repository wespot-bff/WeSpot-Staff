package com.wespot.staff.vote.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.wespot.staff.domain.vote.VoteQuestionContent
import com.wespot.staff.vote.write.QuestionWriteComponent
import com.wespot.staff.vote.home.VoteHomeComponent
import com.wespot.staff.vote.navigation.VoteRootComponent.VoteChild
import com.wespot.staff.vote.question.QuestionComponent
import com.wespot.staff.vote.write.confirm.QuestionConfirmComponent
import kotlinx.serialization.Serializable

interface VoteRootComponent {
    val stack: Value<ChildStack<*, VoteChild>>

    fun isBottomBarImpression(voteChild: VoteChild): Boolean

    fun popBackStack()

    sealed class VoteChild {
        class VoteHomeScreen(val component: VoteHomeComponent) : VoteChild()
        class QuestionScreen(val component: QuestionComponent) : VoteChild()
        class QuestionWriteScreen(val component: QuestionWriteComponent) : VoteChild()
        class QuestionConfirmScreen(val component: QuestionConfirmComponent) : VoteChild()
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

    /** 바텀 네비게이션 바 노출 화면 지정 */
    override fun isBottomBarImpression(voteChild: VoteChild): Boolean =
        voteChild is VoteChild.VoteHomeScreen

    override fun popBackStack() {
        navigation.pop()
    }

    private fun createChild(config: VoteConfiguration, componentContext: ComponentContext): VoteChild =
        when (config) {
            is VoteConfiguration.VoteHome -> VoteChild.VoteHomeScreen(voteHomeComponent(componentContext))
            is VoteConfiguration.Question -> VoteChild.QuestionScreen(questionComponent(componentContext, config))
            is VoteConfiguration.QuestionWrite -> VoteChild.QuestionWriteScreen(questionWriteComponent(componentContext))
            is VoteConfiguration.QuestionConfirm -> VoteChild.QuestionConfirmScreen(questionConfirmComponent(componentContext, config))
        }

    private fun voteHomeComponent(componentContext: ComponentContext): VoteHomeComponent =
        VoteHomeComponent(
            componentContext = componentContext,
            navigateToQuestion = {
                navigation.pushToFront(VoteConfiguration.Question())
            },
            navigateToQuestionWrite = {
                navigation.pushToFront(VoteConfiguration.QuestionWrite)
            }
        )

    private fun questionComponent(
        componentContext: ComponentContext,
        config : VoteConfiguration.Question,
    ): QuestionComponent =
        QuestionComponent(componentContext = componentContext, popBackStack = ::popBackStack, toastMessage = config.toastMessage)

    private fun questionWriteComponent(componentContext: ComponentContext): QuestionWriteComponent =
        QuestionWriteComponent(
            componentContext = componentContext,
            popBackStack = ::popBackStack,
            navigateToQuestionConfirm = {
                navigation.pushToFront(
                    VoteConfiguration.QuestionConfirm(
                        it
                    )
                )
            },
            navigateToQuestion = { navigation.pushToFront(VoteConfiguration.Question(it)) },
        )

    private fun questionConfirmComponent(
        componentContext: ComponentContext,
        config : VoteConfiguration.QuestionConfirm,
    ): QuestionConfirmComponent =
        QuestionConfirmComponent(
            componentContext = componentContext,
            questionList = config.questionList,
            popBackStack = ::popBackStack,
            navigateToQuestion = { navigation.pushToFront(VoteConfiguration.Question(it)) },
        )

    @Serializable
    sealed interface VoteConfiguration {
        @Serializable
        data object VoteHome : VoteConfiguration

        @Serializable
        data class Question(val toastMessage: String? = null) : VoteConfiguration

        @Serializable
        data object QuestionWrite : VoteConfiguration

        @Serializable
        data class QuestionConfirm(val questionList: List<VoteQuestionContent>) : VoteConfiguration
    }
}
