package com.wespot.staff.data.config

import com.wespot.staff.data.config.model.RemoteConfigDto
import com.wespot.staff.data.config.remote.RemoteConfigDataSource
import com.wespot.staff.domain.config.RemoteConfig
import com.wespot.staff.domain.config.RemoteConfigKey
import com.wespot.staff.domain.config.RemoteConfigRepository

class DefaultRemoteConfigRepository(
    private val dataSource: RemoteConfigDataSource,
): RemoteConfigRepository {
    override suspend fun startRemoteConfig(): Boolean =
        dataSource.startRemoteConfig()

    override fun fetchFromRemoteConfig(): List<RemoteConfig> =
        /** 등록되어 있지 않은 RemoteConfig 값은 Description이 생략된다. */
        dataSource.fetchFromRemoteConfig()
            .mapNotNull { entry ->
                RemoteConfig(
                    key = entry.key,
                    description = getRemoteConfigDescription(entry.key),
                    value = entry.value.asString(),
                )
            }

    override suspend fun getRemoteConfigValue(): Result<List<RemoteConfig>> =
        dataSource.getRemoteConfigValue()
            .mapCatching { remoteConfigList ->
                remoteConfigList.map {
                    RemoteConfig(
                        key = it.key,
                        description = getRemoteConfigDescription(it.key),
                        value = it.value,
                    )
                }
            }

    override suspend fun postRemoteConfigValue(remoteConfig: RemoteConfig): Result<Unit> =
        dataSource.postRemoteConfigValue(remoteConfig = RemoteConfigDto(remoteConfig.key, remoteConfig.value))

    override suspend fun putRemoteConfigValue(remoteConfig: RemoteConfig): Result<Unit> =
        dataSource.putRemoteConfigValue(remoteConfig = RemoteConfigDto(remoteConfig.key, remoteConfig.value))

    override suspend fun deleteRemoteConfigValue(key: String): Result<Unit> =
        dataSource.deleteRemoteConfigValue(key)

    private fun getRemoteConfigDescription(key: String) = runCatching {
        RemoteConfigKey.valueOf(key).toDescription()
    }.getOrDefault("")
}
