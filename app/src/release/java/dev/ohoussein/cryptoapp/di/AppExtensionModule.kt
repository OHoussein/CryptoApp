package dev.ohoussein.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.config.IAppFlavorSetup
import dev.ohoussein.cryptoapp.config.ReleaseAppSetup
import dev.ohoussein.cryptoapp.data.di.DIConstants
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppExtensionModule {

    @Provides
    @Singleton
    fun provideAppFlavorSetup(): IAppFlavorSetup = ReleaseAppSetup()

    @Provides
    @Named(DIConstants.Qualifier.HTTP_NETWORK_INTERCEPTOR)
    @Singleton
    fun provideHttpNetworkInterceptors(): Array<Interceptor> = emptyArray()

    @Provides
    @Named(DIConstants.Qualifier.HTTP_INTERCEPTOR)
    @Singleton
    fun provideHttpInterceptors(): Array<Interceptor> = emptyArray()
}
