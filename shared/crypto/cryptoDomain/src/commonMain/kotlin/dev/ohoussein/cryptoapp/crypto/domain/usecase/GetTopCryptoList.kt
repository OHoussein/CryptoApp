package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetTopCryptoList : KoinComponent {

    private val cryptoRepository: ICryptoRepository by inject()

    operator fun invoke(): Flow<List<DomainCrypto>> {
        return cryptoRepository.getTopCryptoList()
    }

    suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
