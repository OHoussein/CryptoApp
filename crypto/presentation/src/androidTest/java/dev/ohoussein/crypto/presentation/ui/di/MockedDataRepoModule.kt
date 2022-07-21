package dev.ohoussein.crypto.presentation.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import javax.inject.Singleton
import org.mockito.kotlin.mock

@Module
@InstallIn(SingletonComponent::class)
object MockedDataRepoModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(): ICryptoRepository = mock()
}

