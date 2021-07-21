package dev.ohoussein.cryptoapp.data.network

import dev.ohoussein.cryptoapp.data.BuildConfig
import dev.ohoussein.cryptoapp.data.Config
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkBuilder {
    fun createConverter(): Converter.Factory = MoshiConverterFactory.create()

    fun createOkHttp(
        httpInterceptor: Array<Interceptor>,
        httpNetWorkInterceptor: Array<Interceptor>
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                httpInterceptor.forEach {
                    addInterceptor(it)
                }
                httpNetWorkInterceptor.forEach {
                    addNetworkInterceptor(it)
                }
            }
            .build()

    fun createRetrofit(
        baseUrl: HttpUrl = Config.API_BASE_URL.toHttpUrl(),
        okHttpClient: OkHttpClient = createOkHttp(emptyArray(), emptyArray()),
        converterFactory: Converter.Factory = createConverter(),
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(converterFactory)
        .baseUrl(baseUrl)
        .validateEagerly(BuildConfig.DEBUG)
        .client(okHttpClient)
        .build()

    fun createApiService(retrofit: Retrofit): ApiCoinGeckoService =
        retrofit.create(ApiCoinGeckoService::class.java)
}
