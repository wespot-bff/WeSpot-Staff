package com.wespot.staff.data.config.remote

import com.wespot.staff.data.config.model.RemoteConfigDto
import com.wespot.staff.data.core.safeRequest
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfigValue
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

interface RemoteConfigDataSource {
    suspend fun startRemoteConfig(): Boolean
    fun fetchFromRemoteConfig(): Map<String, FirebaseRemoteConfigValue>
    suspend fun getRemoteConfigValue(): Result<List<RemoteConfigDto>>
    suspend fun postRemoteConfigValue(remoteConfig: RemoteConfigDto): Result<Unit>
    suspend fun putRemoteConfigValue(remoteConfig: RemoteConfigDto): Result<Unit>
    suspend fun deleteRemoteConfigValue(key: String): Result<Unit>
}

class DefaultRemoteConfigDataSource(
    private val remoteConfig: FirebaseRemoteConfig,
    private val httpClient: HttpClient,
) : RemoteConfigDataSource {
    override suspend fun startRemoteConfig(): Boolean =
        remoteConfig.fetchAndActivate()

    override fun fetchFromRemoteConfig(): Map<String, FirebaseRemoteConfigValue> = remoteConfig.all

    override suspend fun getRemoteConfigValue(): Result<List<RemoteConfigDto>> =
        httpClient.safeRequest {
            url {
                path("/admin/remote-config")
            }
            method = HttpMethod.Get
        }

    override suspend fun postRemoteConfigValue(remoteConfig: RemoteConfigDto): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/remote-config")
                setBody(remoteConfig)
            }
            method = HttpMethod.Post
        }

    override suspend fun putRemoteConfigValue(remoteConfig: RemoteConfigDto): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/remote-config")
                setBody(remoteConfig)
            }
            method = HttpMethod.Put
        }

    override suspend fun deleteRemoteConfigValue(key: String): Result<Unit> =
        httpClient.safeRequest {
            url {
                path("/admin/remote-config/$key")
            }
            method = HttpMethod.Delete
        }
}
