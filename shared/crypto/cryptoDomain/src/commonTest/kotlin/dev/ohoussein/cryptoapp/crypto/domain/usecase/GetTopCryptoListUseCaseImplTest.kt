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

class GetTopCryptoListUseCaseImplTest : KoinComponent {

    private val topCryptoListUseCase: GetTopCryptoListUseCase by inject()
    private lateinit var cryptoRepository: MockedCryptoRepository

    @BeforeTest
    fun setup() {
        cryptoRepository = MockedCryptoRepository()
        startKoin {
            modules(
                module {
                    single<ICryptoRepository> { cryptoRepository }
                    single<GetTopCryptoListUseCase> { GetTopCryptoListUseCaseImpl() }
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

        topCryptoListUseCase.get()

        assertEquals(1, cryptoRepository.countGetTopCryptoList)
    }

    @Test
    fun getAsWrapper_calls_repository() {
        topCryptoListUseCase.getAsWrapper()

        assertEquals(1, cryptoRepository.countGetTopCryptoList)
    }

    @Test
    fun refreshTopCryptoList_calls_repository() {
        runBlocking { topCryptoListUseCase.refresh() }

        assertEquals(1, cryptoRepository.countRefreshTopCryptoList)
    }
}
