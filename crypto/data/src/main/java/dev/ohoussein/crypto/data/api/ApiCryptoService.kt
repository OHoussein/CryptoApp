package dev.ohoussein.crypto.data.api

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCryptoService {

    @GET("v3/coins/markets")
    suspend fun getTopCrypto(@Query("vs_currency") vsCurrency: String): List<TopCryptoResponse>

    @GET("v3/coins/{id}?tickers=false&market_data=false&community_data=false&developer_data=true&sparkline=false")
    suspend fun getCryptoDetails(@Path("id") cryptoId: String): CryptoDetailsResponse

    companion object {
        fun create(retrofit: Retrofit): ApiCryptoService =
            retrofit.create(ApiCryptoService::class.java)
    }
}
