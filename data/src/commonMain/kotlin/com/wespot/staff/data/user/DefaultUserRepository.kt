package com.wespot.staff.data.user

import com.wespot.staff.data.user.remote.UserApiClient
import com.wespot.staff.domain.user.User
import com.wespot.staff.domain.user.UserRepository

public class DefaultUserRepository(
    private val userApi: UserApiClient,
): UserRepository {
    override suspend fun getUsers(): Result<List<User>> =
        userApi.getUsers().mapCatching { responses ->
            responses.map { it.toUser() }
        }

    override suspend fun deleteUser(userId: Long): Result<Unit> =
        userApi.deleteUser(userId)
}
