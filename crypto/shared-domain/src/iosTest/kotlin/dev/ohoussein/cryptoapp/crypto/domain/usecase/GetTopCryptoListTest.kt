package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTopCryptoListTest {

    @MockK
    lateinit var cryptoRepository: ICryptoRepository

    @InjectMockKs
    lateinit var getTopCryptoList: GetTopCryptoList

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun getTopCryptoList_calls_repository() {
        val data = mockk<List<DomainCrypto>>()
        every { cryptoRepository.getTopCryptoList() } returns flowOf(data)

        getTopCryptoList()

        verify { cryptoRepository.getTopCryptoList() }
    }

    @Test
    fun refreshTopCryptoList_calls_repository() {
        coJustRun { cryptoRepository.refreshTopCryptoList() }

        runBlocking { getTopCryptoList.refresh() }

        coVerify { cryptoRepository.refreshTopCryptoList() }
    }
}

