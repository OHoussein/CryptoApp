package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetCryptoDetails(private val repository: ICryptoRepository) {

    operator fun invoke(cryptoId: String): Flow<DomainCryptoDetails> {
        return repository.getCryptoDetails(cryptoId)
    }
}
