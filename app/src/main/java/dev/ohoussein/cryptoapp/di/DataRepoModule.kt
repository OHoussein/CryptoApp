package dev.ohoussein.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.data.network.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.data.repository.RemoteCryptoRepository
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataRepoModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(
        service: ApiCoinGeckoService,
        mapper: DomainModelMapper
    ): ICryptoRepository =
        RemoteCryptoRepository(service, mapper)

}
