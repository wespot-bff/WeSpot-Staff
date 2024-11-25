package com.wespot.staff.domain.vote

interface VoteRepository {
    suspend fun postVoteQuestion(question: VoteQuestionContent): Result<Unit>
    suspend fun editVoteQuestion(id: Long, question: VoteQuestionContent): Result<Unit>
    suspend fun postVoteQuestions(questions: List<VoteQuestionContent>): Result<Unit>
    suspend fun getVoteQuestions(): Result<List<VoteQuestion>>
}
