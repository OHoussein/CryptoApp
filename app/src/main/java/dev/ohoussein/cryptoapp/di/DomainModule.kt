package dev.ohoussein.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.domain.usecase.GetTopCryptoList

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetTopCryptoListUseCase(repo: ICryptoRepository) = GetTopCryptoList(repo)

}
