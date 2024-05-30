package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "GetCryptoDetailsUseCase", exact = true)
interface GetCryptoDetailsUseCase {
    fun observe(cryptoId: String): Flow<CryptoDetailsModel>

    suspend fun refresh(cryptoId: String)
}
