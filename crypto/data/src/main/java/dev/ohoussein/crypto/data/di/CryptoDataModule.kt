package dev.ohoussein.crypto.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.data.api.ApiCoinGeckoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.database.CryptoDAO
import dev.ohoussein.crypto.data.database.mapper.DbDomainModelMapper
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import retrofit2.Retrofit
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CryptoDataModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(
            service: ApiCoinGeckoService,
            database: CryptoDAO,
            apiMapper: ApiDomainModelMapper,
            dbMapper: DbDomainModelMapper,
    ): ICryptoRepository =
            CryptoRepository(service, database, apiMapper, dbMapper)

    @Provides
    fun provideApiDomainModelMapper(locale: Locale) = ApiDomainModelMapper(locale)

    @Provides
    fun provideDbDomainModelMapper() = DbDomainModelMapper()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiCoinGeckoService = ApiCoinGeckoService.create(retrofit)

}
