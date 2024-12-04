package com.wespot.staff.vote.write

import com.arkivanov.decompose.ComponentContext
import com.wespot.staff.domain.vote.VoteQuestionContent

class QuestionWriteComponent(
    componentContext: ComponentContext,
    val popBackStack: () -> Unit,
    val navigateToQuestionConfirm: (List<VoteQuestionContent>) -> Unit,
    val navigateToQuestion: (String) -> Unit,
): ComponentContext by componentContext {
    fun navigateUp() = popBackStack()

    fun navigateToQuestionConfirmScreen(questionListString: String) =
        navigateToQuestionConfirm(parseQuestionListString(questionListString))

    fun navigateToQuestionScreen(message: String) = navigateToQuestion(message)

    private fun parseQuestionListString(questionListString: String): List<VoteQuestionContent> =
        questionListString
            .split("\n")
            .filter { it.isNotBlank() }
            .map { it.trim() }
}
