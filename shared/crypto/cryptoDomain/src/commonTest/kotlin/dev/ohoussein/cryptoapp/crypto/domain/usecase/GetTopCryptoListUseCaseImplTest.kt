package dev.ohoussein.cryptoapp.crypto.domain.usecase

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.usecase.stub.MockedCryptoRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest

class GetTopCryptoListUseCaseImplTest {

    private lateinit var topCryptoListUseCase: GetTopCryptoListUseCase
    private lateinit var cryptoRepository: MockedCryptoRepository

    @BeforeTest
    fun setup() {
        cryptoRepository = MockedCryptoRepository()
        topCryptoListUseCase = GetTopCryptoListUseCaseImpl(
            repository = cryptoRepository
        )
    }

    @Test
    fun `Given no data WHEN observe IT should return null`() = runTest {
        topCryptoListUseCase.observe().test {
            expectNoEvents()
        }
    }

    @Test
    fun `Given data WHEN refresh IT should return the data`() = runTest {
        topCryptoListUseCase.refresh()
        topCryptoListUseCase.observe().test {
            assertEquals(5, awaitItem().size)
        }
    }
}
