package dev.ohoussein.crypto.domain.repo

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(): Flow<List<DomainCrypto>>

    suspend fun refreshTopCryptoList()

    fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails>

    suspend fun refreshCryptoDetails(cryptoId: String)
}
