package com.wespot.staff.data.user.remote

import com.wespot.staff.data.core.safeRequest
import com.wespot.staff.data.user.model.UserResponse
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import io.ktor.http.path

public interface UserApiClient {
    suspend fun getUsers(): Result<List<UserResponse>>
    suspend fun deleteUser(userId: Long): Result<Unit>
}

public class DefaultUserApiClient(
    private val httpClient: HttpClient,
): UserApiClient {
    override suspend fun getUsers(): Result<List<UserResponse>> =
        httpClient.safeRequest {
            url {
                path("/admin/users")
            }
            method = HttpMethod.Get
        }

    override suspend fun deleteUser(userId: Long): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/users/$userId")
            }
            method = HttpMethod.Delete
        }
}
