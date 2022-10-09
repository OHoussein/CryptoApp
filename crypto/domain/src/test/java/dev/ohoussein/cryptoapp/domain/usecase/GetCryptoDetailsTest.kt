package dev.ohoussein.cryptoapp.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class GetCryptoDetailsTest {

    private lateinit var tested: GetCryptoDetails
    private lateinit var repository: ICryptoRepository

    private val cryptoId = "bitcoin"

    @Before
    fun setup() {
        repository = mock()
        tested = GetCryptoDetails(repository)
    }

    @Test
    fun `should call get crypto details`() {
        // Given
        val data = mock<DomainCryptoDetails>()
        whenever(repository.getCryptoDetails(cryptoId)).thenReturn(flowOf(data))
        // When
        tested(cryptoId)
        // Then
        verify(repository).getCryptoDetails(cryptoId)
    }
}
