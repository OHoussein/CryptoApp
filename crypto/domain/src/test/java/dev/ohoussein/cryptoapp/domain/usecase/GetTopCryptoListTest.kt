package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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
        // Given
        val data = mock<List<DomainCrypto>>()
        val dataFlow = flowOf(data)
        val vsCurrency = "USD"
        whenever(repository.getTopCryptoList(vsCurrency)).thenReturn(dataFlow)
        // When
        tested.get(vsCurrency)
        // Then
        verify(repository).getTopCryptoList(vsCurrency)
    }

    @Test
    fun `should call get refresh top crypto list`(): Unit = runBlocking {
        // Given
        val vsCurrency = "USD"
        whenever(repository.refreshTopCryptoList(vsCurrency)).thenReturn(Unit)
        // When
        tested.refresh(vsCurrency)
        // Then
        verify(repository).refreshTopCryptoList(vsCurrency)
    }
}
