package com.wespot.staff.domain.vote

import kotlinx.coroutines.flow.Flow

interface VoteRepository {
    fun getVoteQuestionsStream(): Flow<List<VoteQuestion>>
    suspend fun postVoteQuestion(question: VoteQuestionContent): Result<Unit>
    suspend fun editVoteQuestion(id: Long, question: VoteQuestionContent): Result<Unit>
}
