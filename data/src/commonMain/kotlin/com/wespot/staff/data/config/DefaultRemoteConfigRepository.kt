package com.wespot.staff.data.config

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

    private fun getRemoteConfigDescription(key: String) = runCatching {
        RemoteConfigKey.valueOf(key).toDescription()
    }.getOrDefault("")
}
