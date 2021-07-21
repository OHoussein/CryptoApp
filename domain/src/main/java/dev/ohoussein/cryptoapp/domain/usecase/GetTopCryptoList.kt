package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val cryptoRepository: ICryptoRepository) {

    fun get(vsCurrency: String): Flow<List<DomainCrypto>> {
        return cryptoRepository.getTopCryptoList(vsCurrency)
    }

    suspend fun refresh(vsCurrency: String) {
        cryptoRepository.refreshTopCryptoList(vsCurrency)
    }
}
