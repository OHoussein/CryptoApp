package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.crypto.domain.usecase.stub.MockedCryptoRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class GetCryptoDetailsTest : KoinComponent {

    private val cryptoId = "bitcoin"

    private val getCryptoDetails: GetCryptoDetails by inject()
    private lateinit var cryptoRepository: MockedCryptoRepository

    @BeforeTest
    fun setup() {
        cryptoRepository = MockedCryptoRepository()
        startKoin {
            modules(
                module {
                    single<ICryptoRepository> { cryptoRepository }
                    single { GetCryptoDetails() }
                }
            )
        }
    }

    @AfterTest
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun getCryptoDetails_calls_repository() {
        getCryptoDetails(cryptoId)

        assertEquals(listOf(cryptoId), cryptoRepository.getCryptoDetailsParams)
    }

    @Test
    fun refreshCryptoDetails_calls_repository() {
        runBlocking { getCryptoDetails.refresh(cryptoId) }

        assertEquals(listOf(cryptoId), cryptoRepository.refreshCryptoDetailsParams)
    }
}
