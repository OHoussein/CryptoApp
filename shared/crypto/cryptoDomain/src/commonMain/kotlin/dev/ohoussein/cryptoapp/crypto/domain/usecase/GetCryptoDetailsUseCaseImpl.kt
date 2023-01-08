package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.core.coroutinestools.wrap
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetCryptoDetailsUseCaseImpl : KoinComponent, GetCryptoDetailsUseCase {

    private val repository: ICryptoRepository by inject()

    override fun get(cryptoId: String): Flow<DomainCryptoDetails> {
        return repository.getCryptoDetails(cryptoId)
    }

    override fun getAsWrapper(cryptoId: String): FlowWrapper<DomainCryptoDetails> = get(cryptoId).wrap()

    @Throws(Throwable::class)
    override suspend fun refresh(cryptoId: String) {
        repository.refreshCryptoDetails(cryptoId)
    }
}
