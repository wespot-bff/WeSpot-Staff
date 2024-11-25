package com.wespot.staff.data.di

import com.wespot.staff.data.config.DefaultRemoteConfigRepository
import com.wespot.staff.data.config.remote.RemoteConfigDataSource
import com.wespot.staff.data.config.remote.DefaultRemoteConfigDataSource
import com.wespot.staff.data.core.createDataStore
import com.wespot.staff.data.core.defaultKtorConfig
import com.wespot.staff.data.notification.NotificationApiClient
import com.wespot.staff.data.notification.DefaultNotificationApiClient
import com.wespot.staff.data.notification.DefaultNotificationRepository
import com.wespot.staff.data.vote.DefaultVoteRepository
import com.wespot.staff.data.vote.local.DefaultVoteDataStore
import com.wespot.staff.data.vote.local.VoteDataStore
import com.wespot.staff.data.vote.remote.DefaultVoteApiClient
import com.wespot.staff.data.vote.remote.VoteApiClient
import com.wespot.staff.domain.notification.NotificationRepository
import com.wespot.staff.domain.vote.VoteRepository
import com.wespot.staff.domain.config.RemoteConfigRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.remoteConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

public actual val dataModule: Module = module {
    // Remote
    single {
        HttpClient(OkHttp) {
            defaultKtorConfig()
        }
    }

    // Local
    single<VoteDataStore> {
        DefaultVoteDataStore(createDataStore(androidContext()))
    }

    // Source
    single<FirebaseRemoteConfig> {
        Firebase.remoteConfig
    }

    // Repository
    single<Repositories> {
        DefaultRepositories(
            mapOf(
                VoteRepository::class to get<VoteRepository>(),
            )
        )
    }

    // Binds
    singleOf(::DefaultVoteApiClient) bind VoteApiClient::class
    singleOf(::DefaultVoteRepository) bind VoteRepository::class
    singleOf(::DefaultRemoteConfigDataSource) bind RemoteConfigDataSource::class
    singleOf(::DefaultRemoteConfigRepository) bind RemoteConfigRepository::class
    singleOf(::DefaultNotificationApiClient) bind NotificationApiClient::class
    singleOf(::DefaultNotificationRepository) bind NotificationRepository::class
}
