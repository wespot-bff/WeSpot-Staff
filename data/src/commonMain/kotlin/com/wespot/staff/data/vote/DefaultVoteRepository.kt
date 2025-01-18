package com.wespot.staff.data.vote

import com.wespot.staff.data.vote.local.VoteDataStore
import com.wespot.staff.data.vote.model.VoteQuestionRequest
import com.wespot.staff.data.vote.remote.VoteApiClient
import com.wespot.staff.domain.vote.VoteQuestion
import com.wespot.staff.domain.vote.VoteQuestionContent
import com.wespot.staff.domain.vote.VoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

public class DefaultVoteRepository(
    private val voteApi: VoteApiClient,
    private val voteDataStore: VoteDataStore,
): VoteRepository {
    override suspend fun postVoteQuestion(question: VoteQuestionContent): Result<Unit> {
        return voteApi.postVoteQuestion(VoteQuestionRequest(question))
    }

    override suspend fun editVoteQuestion(id: Long, question: VoteQuestionContent): Result<Unit> {
        return voteApi.editVoteQuestion(id, question = VoteQuestionRequest(question))
    }

    override suspend fun postVoteQuestions(questions: List<VoteQuestionContent>): Result<Unit> =
        voteApi.postVoteQuestions(questions.map { VoteQuestionRequest(it) })

    override suspend fun getVoteQuestions(): Result<List<VoteQuestion>> =
        voteApi.getVoteQuestions().mapCatching {
            it.voteOptions.map { list ->
                list.toVoteQuestion()
            }.reversed()
        }.onSuccess { voteQuestions ->
            refreshVoteQuestions(voteQuestions)
        }.recoverCatching {
            getVoteQuestionsStream().first()
        }

    private suspend fun refreshVoteQuestions(questions: List<VoteQuestion>) = runCatching {
        voteDataStore.save(questions)
    }

    private fun getVoteQuestionsStream(): Flow<List<VoteQuestion>> {
        return voteDataStore.getVoteQuestionsStream()
    }
}
