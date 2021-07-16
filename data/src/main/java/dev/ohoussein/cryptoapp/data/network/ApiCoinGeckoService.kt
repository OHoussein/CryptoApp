package dev.ohoussein.cryptoapp.data.network

import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCoinGeckoService {

    @GET("v3/coins/markets")
    suspend fun getTopCrypto(@Query("vs_currency") vsCurrency: String): List<TopCryptoResponse>
}
