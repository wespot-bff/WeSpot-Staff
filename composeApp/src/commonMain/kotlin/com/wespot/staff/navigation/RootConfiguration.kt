package com.wespot.staff.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootConfiguration {
    @Serializable
    data object Home : RootConfiguration

    @Serializable
    data object Vote : RootConfiguration

    @Serializable
    data object Report : RootConfiguration
}
