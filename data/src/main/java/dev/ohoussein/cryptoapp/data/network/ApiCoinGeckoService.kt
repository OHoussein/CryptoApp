package dev.ohoussein.cryptoapp.data.network

import dev.ohoussein.cryptoapp.data.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCoinGeckoService {

    @GET("v3/coins/markets")
    suspend fun getTopCrypto(@Query("vs_currency") vsCurrency: String): List<TopCryptoResponse>

    @GET("v3/coins/{id}?tickers=false&market_data=false&community_data=false&developer_data=true&sparkline=false")
    suspend fun getCryptoDetails(@Path("id") cryptoId: String): CryptoDetailsResponse
}
