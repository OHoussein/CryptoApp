package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.FakeCryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetCryptoDetailsUseCase : GetCryptoDetailsUseCase {
    var shouldThrowOnRefresh = false

    override fun observe(cryptoId: String): Flow<CryptoDetailsModel> {
        val cryptoDetails = FakeCryptoModel.cryptoDetails(cryptoId)
        return flowOf(cryptoDetails)
    }

    override suspend fun refresh(cryptoId: String) {
        if (shouldThrowOnRefresh) {
            throw Error()
        }
    }

    override suspend fun getHistoricalPrices(cryptoId: String, days: Int): Result<List<HistoricalPrice>> {
        return if (shouldThrowOnRefresh) {
            Result.failure(Exception())
        } else {
            Result.success(FakeCryptoModel.historicalPrices(days))
        }
    }
}
