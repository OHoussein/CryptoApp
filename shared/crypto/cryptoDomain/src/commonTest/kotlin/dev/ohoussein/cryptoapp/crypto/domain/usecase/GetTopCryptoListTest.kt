package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.crypto.domain.usecase.stub.MockedCryptoRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.runBlocking

class GetTopCryptoListTest {

    @Test
    fun getTopCryptoList_calls_repository() {
        val cryptoRepository = MockedCryptoRepository()
        val getTopCryptoList = GetTopCryptoList(cryptoRepository)

        getTopCryptoList()

        assertEquals(1, cryptoRepository.countGetTopCryptoList)
    }

    @Test
    fun refreshTopCryptoList_calls_repository() {
        val cryptoRepository = MockedCryptoRepository()
        val getTopCryptoList = GetTopCryptoList(cryptoRepository)

        runBlocking { getTopCryptoList.refresh() }

        assertEquals(1, cryptoRepository.countRefreshTopCryptoList)
    }
}
