package dev.ohoussein.cryptoapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import dev.ohoussein.cryptoapp.data.network.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RemoteCryptoRepositoryTest {

    private lateinit var repository: ICryptoRepository

    @Mock
    private lateinit var apiService: ApiCoinGeckoService

    @Mock
    private lateinit var mapper: DomainModelMapper

    private val vsCurrency = "USDT"

    @Before
    fun setup() {
        repository = RemoteCryptoRepository(apiService, mapper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTopCryptoTest() = runBlockingTest {
        //Given
        val apiResponse = mock<List<TopCryptoResponse>>()
        val domainData = mock<List<DomainCrypto>>()
        whenever(apiService.getTopCrypto(vsCurrency)).thenReturn(apiResponse)
        whenever(mapper.convert(apiResponse)).thenReturn(domainData)
        //When
        val result = repository.getTopCryptoList(vsCurrency).first()
        //Then
        assertNotNull(result)
        assertEquals(domainData, result)
    }
}