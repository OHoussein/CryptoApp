package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCryptoDetailsTest {

    private val cryptoId = "bitcoin"

    @MockK
    lateinit var cryptoRepository: ICryptoRepository

    @InjectMockKs
    lateinit var getCryptoDetails: GetCryptoDetails

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getCryptoDetails_calls_repository() {
        every { cryptoRepository.getCryptoDetails(cryptoId) } returns flowOf()

        getCryptoDetails(cryptoId)

        verify { cryptoRepository.getCryptoDetails(cryptoId) }
    }

    @Test
    fun refreshCryptoDetails_calls_repository() {
        coJustRun { cryptoRepository.refreshCryptoDetails(cryptoId) }

        runBlocking { getCryptoDetails.refresh(cryptoId) }

        coVerify { cryptoRepository.refreshCryptoDetails(cryptoId) }
    }
}
