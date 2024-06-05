package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "GetTopCryptoListUseCase", exact = true)
interface GetTopCryptoListUseCase {
    fun observe(): Flow<List<CryptoModel>>

    suspend fun refresh()
}
