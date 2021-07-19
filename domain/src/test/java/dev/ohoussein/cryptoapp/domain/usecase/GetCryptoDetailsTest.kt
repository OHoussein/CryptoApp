package dev.ohoussein.cryptoapp.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class GetCryptoDetailsTest {

    private lateinit var tested: GetCryptoDetails
    private lateinit var remoteRepository: ICryptoRepository

    private val cryptoId = "bitcoin"

    @Before
    fun setup() {
        remoteRepository = mock()
        tested = GetCryptoDetails(remoteRepository)
    }

    @Test
    fun `should call get crypto details`() {
        //Given
        val data = mock<DomainCryptoDetails>()
        whenever(remoteRepository.getCryptoDetails(cryptoId)).thenReturn(flowOf(data))
        //When
        tested(cryptoId)
        //Then
        verify(remoteRepository).getCryptoDetails(cryptoId)

    }
}