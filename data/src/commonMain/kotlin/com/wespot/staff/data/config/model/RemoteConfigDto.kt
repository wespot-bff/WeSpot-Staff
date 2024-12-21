package com.wespot.staff.data.config.model

import kotlinx.serialization.Serializable

@Serializable
data class RemoteConfigDto(
    val key: String,
    val value: String,
)
