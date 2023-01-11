package dev.ohoussein.cryptoapp.crypto.domain.usecase.stub

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoListModel
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class MockedCryptoRepository : ICryptoRepository {

    var countGetTopCryptoList = 0
    var countRefreshTopCryptoList = 0
    val getCryptoDetailsParams = mutableListOf<String>()
    val refreshCryptoDetailsParams = mutableListOf<String>()

    override fun getTopCryptoList(): Flow<CryptoListModel> {
        countGetTopCryptoList++
        return flowOf()
    }

    override suspend fun refreshTopCryptoList() {
        countRefreshTopCryptoList++
    }

    override fun getCryptoDetails(cryptoId: String): Flow<CryptoDetailsModel> {
        getCryptoDetailsParams += cryptoId
        return flowOf()
    }

    override suspend fun refreshCryptoDetails(cryptoId: String) {
        refreshCryptoDetailsParams += cryptoId
    }
}
