package dev.ohoussein.cryptoapp.data.database.crypto

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import kotlinx.coroutines.flow.Flow

interface CryptoDAO {
    suspend fun insert(cryptoList: List<CryptoModel>)
    fun selectAll(): Flow<List<CryptoModel>>

    suspend fun insert(cryptoDetails: CryptoDetailsModel)
    fun selectDetails(cryptoDetailsId: String): Flow<CryptoDetailsModel?>
}
