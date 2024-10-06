package bff.wespot.staff.data.vote

import bff.wespot.staff.data.vote.model.VoteQuestionRequest
import bff.wespot.staff.data.vote.remote.VoteApiClient
import bff.wespot.staff.domain.vote.VoteQuestion
import bff.wespot.staff.domain.vote.VoteQuestionContent
import bff.wespot.staff.domain.vote.VoteRepository

public class DefaultVoteRepository(
    private val voteApi: VoteApiClient,
): VoteRepository {
    override suspend fun getVoteQuestions(): Result<List<VoteQuestion>> =
        voteApi.getVoteQuestions().mapCatching {
            it.voteOptions.map { list ->
                list.toVoteQuestion()
            }
        }

    override suspend fun postVoteQuestion(question: VoteQuestionContent): Result<Unit> =
        voteApi.postVoteQuestion(VoteQuestionRequest(question))

    override suspend fun editVoteQuestion(id: Long, question: VoteQuestionContent): Result<Unit> =
        voteApi.editVoteQuestion(id, question = VoteQuestionRequest(question))
}
