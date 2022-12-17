package dev.ohoussein.cryptoapp.crypto.domain.repo

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(): Flow<CryptoList>

    suspend fun refreshTopCryptoList()

    fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails>

    suspend fun refreshCryptoDetails(cryptoId: String)
}
