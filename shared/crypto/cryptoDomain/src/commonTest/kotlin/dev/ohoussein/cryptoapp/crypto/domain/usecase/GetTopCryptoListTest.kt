package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
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

class GetTopCryptoListTest : KoinComponent {

    private val getTopCryptoList: GetTopCryptoList by inject()
    private lateinit var cryptoRepository: MockedCryptoRepository

    @BeforeTest
    fun setup() {
        cryptoRepository = MockedCryptoRepository()
        startKoin {
            modules(
                module {
                    single<ICryptoRepository> { cryptoRepository }
                    single { GetTopCryptoList() }
                }
            )
        }
    }

    @AfterTest
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun getTopCryptoList_calls_repository() {

        getTopCryptoList()

        assertEquals(1, cryptoRepository.countGetTopCryptoList)
    }

    @Test
    fun refreshTopCryptoList_calls_repository() {
        runBlocking { getTopCryptoList.refresh() }

        assertEquals(1, cryptoRepository.countRefreshTopCryptoList)
    }
}
