package dev.ohoussein.cryptoapp.crypto.domain.usecase.stub

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.model.FakeCryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

internal class MockedCryptoRepository : ICryptoRepository {

    private val cryptoList = MutableStateFlow<List<CryptoModel>?>(null)
    private val cryptoDetails = MutableStateFlow<CryptoDetailsModel?>(null)

    override fun getTopCryptoList(): Flow<List<CryptoModel>> {
        return cryptoList.filterNotNull()
    }

    override suspend fun refreshTopCryptoList() {
        cryptoList.value = FakeCryptoModel.cryptoList(5)
    }

    override fun getCryptoDetails(cryptoId: String): Flow<CryptoDetailsModel> {
        return cryptoDetails.filterNotNull()
    }

    override suspend fun refreshCryptoDetails(cryptoId: String) {
        cryptoDetails.value = FakeCryptoModel.cryptoDetails()
    }
}
