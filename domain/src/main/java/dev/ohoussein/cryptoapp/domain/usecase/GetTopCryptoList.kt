package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val remoteICryptoRepository: ICryptoRepository) {

    operator fun invoke(vsCurrency: String): Flow<List<DomainCrypto>> {
        return remoteICryptoRepository.getTopCryptoList(vsCurrency)
    }
}
