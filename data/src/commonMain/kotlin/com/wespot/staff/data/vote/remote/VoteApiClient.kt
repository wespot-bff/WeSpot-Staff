package com.wespot.staff.data.vote.remote

import com.wespot.staff.data.core.safeRequest
import com.wespot.staff.data.vote.model.VoteQuestionRequest
import com.wespot.staff.data.vote.model.VoteQuestionsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

public interface VoteApiClient {
    suspend fun getVoteQuestions(): Result<VoteQuestionsResponse>
    suspend fun postVoteQuestion(question: VoteQuestionRequest): Result<Unit>
    suspend fun postVoteQuestions(questions: List<VoteQuestionRequest>): Result<Unit>
    suspend fun editVoteQuestion(id: Long, question: VoteQuestionRequest): Result<Unit>
}

public class DefaultVoteApiClient(
    private val httpClient: HttpClient,
): VoteApiClient {
    override suspend fun getVoteQuestions(): Result<VoteQuestionsResponse> =
        httpClient.safeRequest {
            url {
                path("/admin/vote-options")
            }
            method = HttpMethod.Get
        }

    override suspend fun postVoteQuestion(question: VoteQuestionRequest): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/vote-options")
                setBody(question)
            }
            method = HttpMethod.Post
        }

    override suspend fun postVoteQuestions(questions: List<VoteQuestionRequest>): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/vote-options/bulk")
                setBody(questions)
            }
            method = HttpMethod.Post
        }

    override suspend fun editVoteQuestion(id: Long, question: VoteQuestionRequest): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/vote-options/$id")
                setBody(question)
            }
            method = HttpMethod.Put
        }
}
