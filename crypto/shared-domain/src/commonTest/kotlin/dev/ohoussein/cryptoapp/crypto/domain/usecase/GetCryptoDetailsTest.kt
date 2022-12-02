package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.cryptoapp.crypto.domain.usecase.stub.MockedCryptoRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking

class GetCryptoDetailsTest {

    private val cryptoId = "bitcoin"

    @Test
    fun getCryptoDetails_calls_repository() {
        val cryptoRepository = MockedCryptoRepository()
        val getCryptoDetails = GetCryptoDetails(cryptoRepository)

        getCryptoDetails(cryptoId)

        assertEquals(listOf(cryptoId), cryptoRepository.getCryptoDetailsParams)
    }

    @Test
    fun refreshCryptoDetails_calls_repository() {
        val cryptoRepository = MockedCryptoRepository()
        val getCryptoDetails = GetCryptoDetails(cryptoRepository)

        runBlocking { getCryptoDetails.refresh(cryptoId) }

        assertEquals(listOf(cryptoId), cryptoRepository.refreshCryptoDetailsParams)
    }
}
