package dev.ohoussein.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.common.coroutine.CoroutineContextProvider
import dev.ohoussein.cryptoapp.core.TestCoroutineContextProvider
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestCoreModule {
    @Provides
    @Singleton
    fun provideCoroutineContextProvider(): CoroutineContextProvider = TestCoroutineContextProvider()

    @Provides
    fun provideLocale(): Locale = Locale.US
}
