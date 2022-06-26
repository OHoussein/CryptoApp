package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val cryptoRepository: ICryptoRepository) {

    fun get(vsCurrency: String): Flow<List<DomainCrypto>> {
        return cryptoRepository.getTopCryptoList(vsCurrency)
    }

    suspend fun refresh(vsCurrency: String) {
        cryptoRepository.refreshTopCryptoList(vsCurrency)
    }
}
