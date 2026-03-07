package com.wespot.staff.domain.user

interface UserRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun deleteUser(userId: Long): Result<Unit>
}
