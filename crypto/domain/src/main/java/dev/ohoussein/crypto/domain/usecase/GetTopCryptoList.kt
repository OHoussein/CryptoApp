package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val cryptoRepository: ICryptoRepository) {

    fun get(): Flow<List<DomainCrypto>> {
        return cryptoRepository.getTopCryptoList()
    }

    suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
