package dev.ohoussein.crypto.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CryptoDataModule {

    @Binds
    @Singleton
    abstract fun bindCryptoRepository(impl: CryptoRepository): ICryptoRepository

    companion object {
        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiCryptoService =
            ApiCryptoService.create(retrofit)
    }
}
