package dev.ohoussein.cryptoapp.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class GetTopCryptoListTest {

    private lateinit var tested: GetTopCryptoList
    private lateinit var remoteRepository: ICryptoRepository

    @Before
    fun setup() {
        remoteRepository = mock()
        tested = GetTopCryptoList(remoteRepository)
    }

    @Test
    fun `should call get top crypto list`() {
        //Given
        val data = mock<List<DomainCrypto>>()
        val dataFlow = flowOf(data)
        val vsCurrency = "USD"
        whenever(remoteRepository.getTopCryptoList(vsCurrency)).thenReturn(dataFlow)
        //When
        tested.invoke(vsCurrency)
        //Then
        verify(remoteRepository).getTopCryptoList(vsCurrency)

    }
}