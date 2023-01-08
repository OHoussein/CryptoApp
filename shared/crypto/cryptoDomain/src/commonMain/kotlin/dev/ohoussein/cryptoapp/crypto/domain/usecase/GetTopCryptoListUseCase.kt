package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import kotlinx.coroutines.flow.Flow

interface GetTopCryptoListUseCase {
    fun get(): Flow<CryptoList>
    fun getAsWrapper(): FlowWrapper<CryptoList>

    @Throws(Throwable::class)
    suspend fun refresh()
}
