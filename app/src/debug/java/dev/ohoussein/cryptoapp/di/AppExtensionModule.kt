package dev.ohoussein.cryptoapp.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.config.DebuggableAppSetup
import dev.ohoussein.cryptoapp.config.IAppFlavorSetup
import dev.ohoussein.cryptoapp.data.network.di.DIConstants
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppExtensionModule {

    @Provides
    @Singleton
    fun provideAppFlavorSetup(): IAppFlavorSetup = DebuggableAppSetup()

    @Provides
    @Named(DIConstants.Qualifier.HTTP_NETWORK_INTERCEPTOR)
    @Singleton
    fun provideHttpNetworkInterceptors(): Array<Interceptor> = arrayOf(StethoInterceptor())

    @Provides
    @Named(DIConstants.Qualifier.HTTP_INTERCEPTOR)
    @Singleton
    fun provideHttpInterceptors(): Array<Interceptor> = arrayOf(
            //Logger
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    )
}
