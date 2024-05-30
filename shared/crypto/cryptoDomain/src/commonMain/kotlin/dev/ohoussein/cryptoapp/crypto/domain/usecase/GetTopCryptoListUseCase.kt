package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "GetTopCryptoListUseCase", exact = true)
interface GetTopCryptoListUseCase {
    fun observe(): Flow<List<CryptoModel>>

    suspend fun refresh()
}
