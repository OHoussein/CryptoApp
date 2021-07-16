package dev.ohoussein.cryptoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.data.network.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.data.network.NetworkBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideConverter(): Converter.Factory = NetworkBuilder.createConverter()

    @Provides
    @Singleton
    fun provideOkHttp(
        @Named(DIConstants.Qualifier.HTTP_INTERCEPTOR) httpInterceptor: Array<Interceptor>,
        @Named(DIConstants.Qualifier.HTTP_NETWORK_INTERCEPTOR) httpNetworkInterceptor: Array<Interceptor>,
    ): OkHttpClient = NetworkBuilder.createOkHttp(httpInterceptor, httpNetworkInterceptor)

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, converterFactory: Converter.Factory): Retrofit =
        NetworkBuilder.createRetrofit(
            okHttpClient = okHttpClient,
            converterFactory = converterFactory,
        )

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiCoinGeckoService =
        NetworkBuilder.createApiService(retrofit)

    @Provides
    fun provideDomainEntityMapper() = DomainModelMapper()

}
