package dev.ohoussein.cryptoapp.crypto.domain.repo

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(): Flow<List<CryptoModel>>

    suspend fun refreshTopCryptoList()

    fun getCryptoDetails(cryptoId: String): Flow<CryptoDetailsModel>

    suspend fun refreshCryptoDetails(cryptoId: String)

    suspend fun getHistoricalPrices(cryptoId: String, days: Int): Result<List<HistoricalPrice>>
}
