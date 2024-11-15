package com.wespot.staff.vote.write.confirm

import com.arkivanov.decompose.ComponentContext
import com.wespot.staff.domain.vote.VoteQuestionContent

class QuestionConfirmComponent(
    componentContext: ComponentContext,
    val questionList: List<VoteQuestionContent>,
    val popBackStack: () -> Unit,
    val navigateToQuestion: (String) -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()

    fun navigateToQuestionScreen(message: String) = navigateToQuestion(message)
}