package dev.ohoussein.cryptoapp.crypto.domain.usecase

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.FakeCryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.usecase.stub.MockedCryptoRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCryptoDetailsUseCaseImplTest {

    private val cryptoId = "bitcoin"

    private lateinit var useCase: GetCryptoDetailsUseCase
    private lateinit var cryptoRepository: MockedCryptoRepository

    @BeforeTest
    fun setup() {
        cryptoRepository = MockedCryptoRepository()
        useCase = GetCryptoDetailsUseCaseImpl(
            repository = cryptoRepository
        )
    }

    @Test
    fun `Given no data WHEN observe IT should return null`() = runTest {
        useCase.observe(cryptoId).test {
            expectNoEvents()
        }
    }

    @Test
    fun `Given data WHEN refresh IT should return the data`() = runTest {
        useCase.refresh(cryptoId)
        useCase.observe(cryptoId).test {
            val item = awaitItem()
            assertEquals(FakeCryptoModel.cryptoDetails(), item)
        }
    }

    @Test
    fun `Given historical prices WHEN getHistoricalPrices IT should return the data`() = runTest {
        val result = useCase.getHistoricalPrices(cryptoId, 7)

        assertEquals(7, result.getOrThrow().size)
    }
}
