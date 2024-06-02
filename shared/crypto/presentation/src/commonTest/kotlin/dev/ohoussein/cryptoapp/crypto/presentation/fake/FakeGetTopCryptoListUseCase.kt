package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.model.FakeCryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetTopCryptoListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetTopCryptoListUseCase : GetTopCryptoListUseCase {
    val cryptoList = FakeCryptoModel.cryptoList(5)
    var shouldThrowOnRefresh = false

    override fun observe(): Flow<List<CryptoModel>> {
        return flowOf(cryptoList)
    }

    override suspend fun refresh() {
        if (shouldThrowOnRefresh) {
            throw Error()
        }
    }
}
