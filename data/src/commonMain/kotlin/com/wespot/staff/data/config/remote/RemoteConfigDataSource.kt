package com.wespot.staff.data.config.remote

import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfigValue

interface RemoteConfigDataSource {
    suspend fun startRemoteConfig(): Boolean
    fun fetchFromRemoteConfig(): Map<String, FirebaseRemoteConfigValue>
}

class DefaultRemoteConfigDataSource(
    private val remoteConfig: FirebaseRemoteConfig,
) : RemoteConfigDataSource {
    override suspend fun startRemoteConfig(): Boolean =
        remoteConfig.fetchAndActivate()

    override fun fetchFromRemoteConfig(): Map<String, FirebaseRemoteConfigValue> = remoteConfig.all
}
