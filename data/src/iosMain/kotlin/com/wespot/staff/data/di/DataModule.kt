package com.wespot.staff.data.di

import com.wespot.staff.data.core.createDataStore
import com.wespot.staff.data.core.defaultKtorConfig
import com.wespot.staff.data.vote.DefaultVoteRepository
import com.wespot.staff.data.vote.local.DefaultVoteDataStore
import com.wespot.staff.data.vote.local.VoteDataStore
import com.wespot.staff.data.vote.remote.DefaultVoteApiClient
import com.wespot.staff.data.vote.remote.VoteApiClient
import com.wespot.staff.domain.vote.VoteRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

public actual val dataModule: Module = module {
    single {
        HttpClient(Darwin) {
            defaultKtorConfig()
        }
    }

    single<VoteDataStore> {
        DefaultVoteDataStore(createDataStore())
    }

    single<Repositories> {
        DefaultRepositories(
            mapOf(
                VoteRepository::class to get<VoteRepository>(),
            )
        )
    }

    singleOf(::DefaultVoteApiClient) bind VoteApiClient::class
    singleOf(::DefaultVoteRepository) bind VoteRepository::class
}
