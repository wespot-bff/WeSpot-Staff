package bff.wespot.staff.domain.vote

interface VoteRepository {
    suspend fun getVoteQuestions(): Result<List<VoteQuestion>>
    suspend fun postVoteQuestion(question: VoteQuestionContent): Result<Unit>
    suspend fun editVoteQuestion(id: Int, question: VoteQuestionContent): Result<Unit>
}
