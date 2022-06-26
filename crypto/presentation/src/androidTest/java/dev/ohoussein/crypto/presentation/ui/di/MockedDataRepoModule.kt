package dev.ohoussein.crypto.presentation.ui.di

import com.nhaarman.mockitokotlin2.mock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MockedDataRepoModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(): ICryptoRepository = mock()

}

