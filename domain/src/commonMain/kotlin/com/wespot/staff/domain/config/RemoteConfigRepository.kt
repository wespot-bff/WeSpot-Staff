package com.wespot.staff.domain.config

interface RemoteConfigRepository {
    suspend fun startRemoteConfig(): Boolean
    fun fetchFromRemoteConfig(): List<RemoteConfig>
    suspend fun getRemoteConfigValue(): Result<List<RemoteConfig>>
    suspend fun postRemoteConfigValue(remoteConfig: RemoteConfig): Result<Unit>
    suspend fun putRemoteConfigValue(remoteConfig: RemoteConfig): Result<Unit>
    suspend fun deleteRemoteConfigValue(key: String): Result<Unit>
}
