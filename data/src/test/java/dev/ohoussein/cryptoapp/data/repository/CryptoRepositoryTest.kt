package dev.ohoussein.cryptoapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.data.database.CryptoDatabase
import dev.ohoussein.cryptoapp.data.database.DBCrypto
import dev.ohoussein.cryptoapp.data.database.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.data.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import dev.ohoussein.cryptoapp.data.network.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CryptoRepositoryTest {

    private lateinit var repository: ICryptoRepository

    @Mock
    private lateinit var apiService: ApiCoinGeckoService

    @Mock
    private lateinit var mapper: DomainModelMapper

    @Mock
    private lateinit var database: CryptoDatabase
    private val cryptoDAO: CryptoDAO = mock()

    private val vsCurrency = "USDT"

    @Before
    fun setup() {
        repository = CryptoRepository(apiService, database, mapper)
        whenever(database.cryptoDAO()).thenReturn(cryptoDAO)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTopCryptoTest() = runBlockingTest {
        //Given
        val dbList = mock<List<DBCrypto>>()
        val domainData = mock<List<DomainCrypto>>()
        whenever(cryptoDAO.getAll()).thenReturn(flowOf(dbList))
        whenever(mapper.convertDBCrypto(dbList)).thenReturn(domainData)
        //When
        val result = repository.getTopCryptoList(vsCurrency).first()
        //Then
        assertNotNull(result)
        assertEquals(domainData, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshTopCryptoTest() = runBlockingTest {
        //Given
        val apiResponse = mock<List<TopCryptoResponse>>()
        val domainData = mock<List<DomainCrypto>>()
        val dbData = mock<List<DBCrypto>>()
        whenever(apiService.getTopCrypto(vsCurrency)).thenReturn(apiResponse)
        whenever(mapper.convert(apiResponse)).thenReturn(domainData)
        whenever(mapper.toDB(domainData)).thenReturn(dbData)
        //When
        repository.refreshTopCryptoList(vsCurrency)
        //Then
        verify(cryptoDAO).insert(dbData)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCryptoDetails() = runBlockingTest {
        //Given
        val cryptoId = "bitcoin"
        val apiResponse = mock<CryptoDetailsResponse>()
        val domainData = mock<DomainCryptoDetails>()
        whenever(apiService.getCryptoDetails(cryptoId)).thenReturn(apiResponse)
        whenever(mapper.convert(apiResponse)).thenReturn(domainData)
        //When
        val result = repository.getCryptoDetails(cryptoId).first()
        //Then
        assertNotNull(result)
        assertEquals(domainData, result)
    }
}