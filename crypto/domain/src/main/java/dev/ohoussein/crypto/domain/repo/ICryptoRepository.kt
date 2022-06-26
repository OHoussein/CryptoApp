package dev.ohoussein.crypto.domain.repo

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(vsCurrency: String): Flow<List<DomainCrypto>>

    suspend fun refreshTopCryptoList(vsCurrency: String)

    fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails>
}
