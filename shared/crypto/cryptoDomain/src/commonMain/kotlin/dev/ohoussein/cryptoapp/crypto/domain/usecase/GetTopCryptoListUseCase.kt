package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.core.coroutinestools.wrap
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetTopCryptoListUseCase : KoinComponent {

    private val cryptoRepository: ICryptoRepository by inject()

    fun get(): Flow<CryptoList> {
        return cryptoRepository.getTopCryptoList()
    }

    fun getAsWrapper(): FlowWrapper<CryptoList> = get().wrap()

    @Throws(Throwable::class)
    suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
