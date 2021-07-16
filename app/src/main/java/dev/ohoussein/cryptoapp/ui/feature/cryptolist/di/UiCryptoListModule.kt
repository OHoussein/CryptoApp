package dev.ohoussein.cryptoapp.ui.feature.cryptolist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dev.ohoussein.cryptoapp.common.coroutine.CoroutineContextProvider
import dev.ohoussein.cryptoapp.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.ui.core.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.HomeViewModel

@Module
@InstallIn(ViewModelComponent::class)
object UiCryptoListModule {

    @Provides
    @ViewModelScoped
    fun provideHomeViewModel(coroutineContextProvider: CoroutineContextProvider,
                                useCase: GetTopCryptoList,
                                modelMapper: DomainModelMapper) =
            HomeViewModel(coroutineContextProvider, useCase, modelMapper)
}
