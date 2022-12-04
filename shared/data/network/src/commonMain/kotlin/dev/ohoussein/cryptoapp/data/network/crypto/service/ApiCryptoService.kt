package dev.ohoussein.cryptoapp.data.network.crypto.service

import dev.ohoussein.cryptoapp.data.network.crypto.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.network.crypto.model.TopCryptoResponse

interface ApiCryptoService {

    suspend fun getTopCrypto(vsCurrency: String): List<TopCryptoResponse>

    suspend fun getCryptoDetails(cryptoId: String): CryptoDetailsResponse
}
