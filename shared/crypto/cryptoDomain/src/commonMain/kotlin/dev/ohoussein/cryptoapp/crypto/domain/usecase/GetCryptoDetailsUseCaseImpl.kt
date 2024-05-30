package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetCryptoDetailsUseCaseImpl(
    private val repository: ICryptoRepository,
) : GetCryptoDetailsUseCase {

    override fun observe(cryptoId: String): Flow<CryptoDetailsModel> {
        return repository.getCryptoDetails(cryptoId)
    }

    override suspend fun refresh(cryptoId: String) {
        repository.refreshCryptoDetails(cryptoId)
    }
}
