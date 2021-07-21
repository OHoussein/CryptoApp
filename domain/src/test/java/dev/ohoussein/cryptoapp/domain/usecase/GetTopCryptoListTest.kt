package dev.ohoussein.cryptoapp.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTopCryptoListTest {

    private lateinit var tested: GetTopCryptoList
    private lateinit var repository: ICryptoRepository

    @Before
    fun setup() {
        repository = mock()
        tested = GetTopCryptoList(repository)
    }

    @Test
    fun `should call get top crypto list`() {
        //Given
        val data = mock<List<DomainCrypto>>()
        val dataFlow = flowOf(data)
        val vsCurrency = "USD"
        whenever(repository.getTopCryptoList(vsCurrency)).thenReturn(dataFlow)
        //When
        tested.get(vsCurrency)
        //Then
        verify(repository).getTopCryptoList(vsCurrency)
    }

    @Test
    fun `should call get refresh top crypto list`(): Unit = runBlocking {
        //Given
        val vsCurrency = "USD"
        whenever(repository.refreshTopCryptoList(vsCurrency)).thenReturn(Unit)
        //When
        tested.refresh(vsCurrency)
        //Then
        verify(repository).refreshTopCryptoList(vsCurrency)
    }
}