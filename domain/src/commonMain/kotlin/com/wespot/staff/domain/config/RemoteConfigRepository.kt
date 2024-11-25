package com.wespot.staff.domain.config

interface RemoteConfigRepository {
    suspend fun startRemoteConfig(): Boolean
    fun fetchFromRemoteConfig(): List<RemoteConfig>
}
