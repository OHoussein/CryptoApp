package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoListModel
import kotlinx.coroutines.flow.Flow

interface GetTopCryptoListUseCase {
    fun get(): Flow<CryptoListModel>
    fun getAsWrapper(): FlowWrapper<CryptoListModel>

    @Throws(Throwable::class)
    suspend fun refresh()
}
