package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoListModel
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "GetTopCryptoListUseCase", exact = true)
interface GetTopCryptoListUseCase {
    fun get(): Flow<CryptoListModel>
    fun getAsWrapper(): FlowWrapper<CryptoListModel>

    @Throws(Throwable::class)
    suspend fun refresh()
}
