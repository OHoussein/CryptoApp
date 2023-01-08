package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.core.coroutinestools.FlowWrapper
import dev.ohoussein.cryptoapp.core.coroutinestools.wrap
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetTopCryptoListUseCaseImpl : KoinComponent, GetTopCryptoListUseCase {

    private val cryptoRepository: ICryptoRepository by inject()

    override fun get(): Flow<CryptoList> {
        return cryptoRepository.getTopCryptoList()
    }

    override fun getAsWrapper(): FlowWrapper<CryptoList> = get().wrap()

    @Throws(Throwable::class)
    override suspend fun refresh() {
        cryptoRepository.refreshTopCryptoList()
    }
}
