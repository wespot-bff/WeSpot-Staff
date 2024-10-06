package bff.wespot.staff.data.di

import bff.wespot.staff.data.core.defaultKtorConfig
import bff.wespot.staff.data.vote.DefaultVoteRepository
import bff.wespot.staff.data.vote.remote.DefaultVoteApiClient
import bff.wespot.staff.data.vote.remote.VoteApiClient
import bff.wespot.staff.domain.vote.VoteRepository
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

    singleOf(::DefaultVoteApiClient) bind VoteApiClient::class
    singleOf(::DefaultVoteRepository) bind VoteRepository::class

    single<Repositories> {
        DefaultRepositories(
            mapOf(
                VoteRepository::class to get<VoteRepository>(),
            )
        )
    }
}
