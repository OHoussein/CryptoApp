package dev.ohoussein.cryptoapp.crypto.domain.usecase.stub

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class MockedCryptoRepository : ICryptoRepository {

    var countGetTopCryptoList = 0
    var countRefreshTopCryptoList = 0
    val getCryptoDetailsParams = mutableListOf<String>()
    val refreshCryptoDetailsParams = mutableListOf<String>()

    override fun getTopCryptoList(): Flow<CryptoList> {
        countGetTopCryptoList++
        return flowOf()
    }

    override suspend fun refreshTopCryptoList() {
        countRefreshTopCryptoList++
    }

    override fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails> {
        getCryptoDetailsParams += cryptoId
        return flowOf()
    }

    override suspend fun refreshCryptoDetails(cryptoId: String) {
        refreshCryptoDetailsParams += cryptoId
    }
}
