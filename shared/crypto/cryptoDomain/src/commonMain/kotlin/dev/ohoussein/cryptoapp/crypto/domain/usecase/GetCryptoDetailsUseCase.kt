package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "GetCryptoDetailsUseCase", exact = true)
interface GetCryptoDetailsUseCase {
    fun get(cryptoId: String): Flow<CryptoDetailsModel>
    fun getAsWrapper(cryptoId: String): FlowWrapper<CryptoDetailsModel>

    @Throws(Throwable::class)
    suspend fun refresh(cryptoId: String)
}
