package dev.ohoussein.core.test.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MockedDataRepoModule {

/*    @Provides
    @Singleton
    fun provideCryptoRepository(): ICryptoRepository = mock()*/
}
