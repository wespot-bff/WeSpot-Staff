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
        /** 일치하지 않는 값을 받은 경우 null을 반환하여 mapNotNull에서 제외된다. */
        dataSource.fetchFromRemoteConfig()
            .mapNotNull { entry ->
                runCatching {
                    RemoteConfig(
                        key = RemoteConfigKey.valueOf(entry.key),
                        value = entry.value.asString()
                    )
                }.getOrNull()
            }
}
