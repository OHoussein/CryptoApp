package dev.ohoussein.cryptoapp.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetTopCryptoListUseCase(repo: ICryptoRepository) = GetTopCryptoList(repo)

    @Provides
    fun provideGetCryptoDetailsUseCase(repo: ICryptoRepository) = GetCryptoDetails(repo)

}
