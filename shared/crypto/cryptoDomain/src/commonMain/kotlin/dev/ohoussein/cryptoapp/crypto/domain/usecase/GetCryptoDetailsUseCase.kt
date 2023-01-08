package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import kotlinx.coroutines.flow.Flow

interface GetCryptoDetailsUseCase {
    fun get(cryptoId: String): Flow<DomainCryptoDetails>
    fun getAsWrapper(cryptoId: String): FlowWrapper<DomainCryptoDetails>

    @Throws(Throwable::class)
    suspend fun refresh(cryptoId: String)
}
