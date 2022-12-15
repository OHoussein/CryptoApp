package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val cryptoRepository: ICryptoRepository) {

    operator fun invoke(): Flow<List<DomainCrypto>> {
        return cryptoRepository.getTopCryptoList()
    }

    suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
