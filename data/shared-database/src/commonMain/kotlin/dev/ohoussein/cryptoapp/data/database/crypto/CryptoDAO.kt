package dev.ohoussein.cryptoapp.data.database.crypto

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import kotlinx.coroutines.flow.Flow

interface CryptoDAO {
    suspend fun insert(cryptoList: List<DomainCrypto>)
    fun selectAll(): Flow<List<DomainCrypto>>

    suspend fun insert(cryptoDetails: DomainCryptoDetails)
    fun selectDetails(cryptoDetailsId: String): Flow<DomainCryptoDetails?>
}
