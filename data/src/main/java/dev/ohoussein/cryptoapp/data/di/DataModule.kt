package dev.ohoussein.cryptoapp.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.ohoussein.crypto.data.database.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.CryptoDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): CryptoDatabase =
        CryptoDatabase.build(context)

    @Provides
    @Singleton
    fun provideCryptoDAO(db: CryptoDatabase): CryptoDAO = db.cryptoDAO()
}
