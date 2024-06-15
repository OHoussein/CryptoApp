package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "GetCryptoDetailsUseCase", exact = true)
interface GetCryptoDetailsUseCase {
    fun observe(cryptoId: String): Flow<CryptoDetailsModel>

    suspend fun refresh(cryptoId: String)

    suspend fun getHistoricalPrices(cryptoId: String, days: Int): Result<List<HistoricalPrice>>
}
