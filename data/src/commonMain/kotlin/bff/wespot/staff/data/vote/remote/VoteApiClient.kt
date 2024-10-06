package bff.wespot.staff.data.vote.remote

import bff.wespot.staff.data.core.safeRequest
import bff.wespot.staff.data.vote.model.VoteQuestionRequest
import bff.wespot.staff.data.vote.model.VoteQuestionResponse
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path

public interface VoteApiClient {
    suspend fun getVoteQuestions(): Result<List<VoteQuestionResponse>>
    suspend fun postVoteQuestion(question: VoteQuestionRequest): Result<Unit>
    suspend fun editVoteQuestion(id: Int, question: VoteQuestionRequest): Result<Unit>
}

public class DefaultVoteApiClient(
    private val httpClient: HttpClient,
): VoteApiClient {
    override suspend fun getVoteQuestions(): Result<List<VoteQuestionResponse>> =
        httpClient.safeRequest {
            url {
                path("/vote-options")
            }
            method = HttpMethod.Get
        }

    override suspend fun postVoteQuestion(question: VoteQuestionRequest): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/vote-options")
            }
            method = HttpMethod.Post
        }

    override suspend fun editVoteQuestion(id: Int, question: VoteQuestionRequest): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/vote-options/$id")
            }
            method = HttpMethod.Put
        }
}
