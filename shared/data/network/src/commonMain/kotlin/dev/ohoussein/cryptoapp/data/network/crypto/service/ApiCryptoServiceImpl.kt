package dev.ohoussein.cryptoapp.data.network.crypto.service

import dev.ohoussein.cryptoapp.data.network.NetworkBuilder
import dev.ohoussein.cryptoapp.data.network.crypto.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.network.crypto.model.TopCryptoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.path

private const val API_BASE_URL = "api.coingecko.com"

internal class ApiCryptoServiceImpl(
    private val httpClient: HttpClient = NetworkBuilder.httpClient(),
    private val baseUrl: String = API_BASE_URL,
) : ApiCryptoService {

    companion object {
        fun create(): ApiCryptoService = ApiCryptoServiceImpl()
    }

    override suspend fun getTopCrypto(vsCurrency: String): List<TopCryptoResponse> = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = baseUrl
            path("/api/v3/coins/markets")
            formData {
                parameter("vs_currency", vsCurrency)
                parameter("sparkline", true)
            }
        }
    }.body()

    override suspend fun getCryptoDetails(cryptoId: String): CryptoDetailsResponse = httpClient.get {
        url {
            protocol = URLProtocol.HTTPS
            host = baseUrl
            path("/api/v3/coins/$cryptoId")
            formData {
                parameter("tickers", false)
                parameter("market_data", false)
                parameter("community_data", false)
                parameter("developer_data", false)
                parameter("sparkline", false)
            }
        }
    }.body()
}
