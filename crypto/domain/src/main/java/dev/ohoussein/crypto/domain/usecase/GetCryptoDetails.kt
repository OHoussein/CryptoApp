package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetCryptoDetails(private val repository: ICryptoRepository) {

    operator fun invoke(cryptoId: String): Flow<DomainCryptoDetails> {
        return repository.getCryptoDetails(cryptoId)
    }

    suspend fun refresh(cryptoId: String) {
        repository.refreshCryptoDetails(cryptoId)
    }
}
