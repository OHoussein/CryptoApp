package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.core.coroutinestools.wrap
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetTopCryptoList : KoinComponent {

    private val cryptoRepository: ICryptoRepository by inject()

    operator fun invoke(): Flow<CryptoList> {
        return cryptoRepository.getTopCryptoList()
    }

    fun get(): FlowWrapper<CryptoList> = invoke().wrap()

    suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
