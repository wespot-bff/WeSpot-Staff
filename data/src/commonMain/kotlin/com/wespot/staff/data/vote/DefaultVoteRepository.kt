package com.wespot.staff.data.vote

import co.touchlab.kermit.Logger
import com.wespot.staff.data.vote.local.VoteDataStore
import com.wespot.staff.data.vote.model.VoteQuestionRequest
import com.wespot.staff.data.vote.remote.VoteApiClient
import com.wespot.staff.domain.vote.VoteQuestion
import com.wespot.staff.domain.vote.VoteQuestionContent
import com.wespot.staff.domain.vote.VoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

public class DefaultVoteRepository(
    private val voteApi: VoteApiClient,
    private val voteDataStore: VoteDataStore,
): VoteRepository {
    override fun getVoteQuestionsStream(): Flow<List<VoteQuestion>> {
        return voteDataStore.getVoteQuestionsStream()
            .onEach { if (it.isEmpty()) refreshVoteQuestions() }
    }

    override suspend fun postVoteQuestion(question: VoteQuestionContent): Result<Unit> {
        return voteApi.postVoteQuestion(VoteQuestionRequest(question)).onSuccess {
            refreshVoteQuestions()
        }
    }

    override suspend fun editVoteQuestion(id: Long, question: VoteQuestionContent): Result<Unit> {
        return voteApi.editVoteQuestion(id, question = VoteQuestionRequest(question)).onSuccess {
            refreshVoteQuestions()
        }
    }

    private suspend fun getVoteQuestions(): Result<List<VoteQuestion>> =
        voteApi.getVoteQuestions().mapCatching {
            it.voteOptions.map { list ->
                list.toVoteQuestion()
            }
        }

    private suspend fun refreshVoteQuestions() = runCatching {
        val voteQuestions = getVoteQuestions().getOrDefault(listOf())
        voteDataStore.save(voteQuestions)
    }
}
