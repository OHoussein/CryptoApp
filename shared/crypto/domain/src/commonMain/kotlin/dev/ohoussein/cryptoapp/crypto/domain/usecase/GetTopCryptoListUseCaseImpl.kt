package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoListUseCaseImpl(
    private val repository: ICryptoRepository,
) : GetTopCryptoListUseCase {

    override fun observe(): Flow<List<CryptoModel>> {
        return repository.getTopCryptoList()
    }

    override suspend fun refresh() {
        repository.refreshTopCryptoList()
    }
}
