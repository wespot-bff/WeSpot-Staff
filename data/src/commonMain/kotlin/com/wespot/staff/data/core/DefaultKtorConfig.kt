package com.wespot.staff.data.core

import WeSpotStaff.data.BuildConfig
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

public fun HttpClientConfig<*>.defaultKtorConfig() {
    defaultRequest {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        url(BuildConfig.BASE_URL)
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                co.touchlab.kermit.Logger.d { message }
            }
        }

        level = LogLevel.BODY
    }

    install(ContentNegotiation) {
        json(
            json = Json {
                encodeDefaults = true
                isLenient = true
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }
}
