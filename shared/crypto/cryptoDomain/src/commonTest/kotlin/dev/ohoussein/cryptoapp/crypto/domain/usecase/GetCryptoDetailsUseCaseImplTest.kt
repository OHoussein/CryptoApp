package dev.ohoussein.cryptoapp.crypto.domain.usecase

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.FakeCryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.usecase.stub.MockedCryptoRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

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
}
