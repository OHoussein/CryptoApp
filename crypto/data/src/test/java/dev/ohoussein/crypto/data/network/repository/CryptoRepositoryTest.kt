package dev.ohoussein.crypto.data.network.repository

import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.CryptoDetailsResponse
import dev.ohoussein.crypto.data.api.TopCryptoResponse
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.database.CryptoDAO
import dev.ohoussein.crypto.data.database.DBCrypto
import dev.ohoussein.crypto.data.database.mapper.DbDomainModelMapper
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CryptoRepositoryTest {

    private lateinit var repository: ICryptoRepository

    @Mock
    private lateinit var apiService: ApiCryptoService

    @Mock
    private lateinit var apiDomainModelMapper: ApiDomainModelMapper

    @Mock
    private lateinit var dbDomainModelMapper: DbDomainModelMapper

    @Mock
    private val cryptoDAO: CryptoDAO = mock()

    private val vsCurrency = "USDT"

    @Before
    fun setup() {
        repository =
            CryptoRepository(apiService, cryptoDAO, apiDomainModelMapper, dbDomainModelMapper)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTopCryptoTest() = runTest {
        // Given
        val dbList = mock<List<DBCrypto>>()
        val domainData = mock<List<DomainCrypto>>()
        whenever(cryptoDAO.getAll()).thenReturn(flowOf(dbList))
        whenever(dbDomainModelMapper.convertDBCrypto(dbList)).thenReturn(domainData)
        // When
        val result = repository.getTopCryptoList(vsCurrency).first()
        // Then
        assertNotNull(result)
        assertEquals(domainData, result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun refreshTopCryptoTest() = runTest {
        // Given
        val apiResponse = mock<List<TopCryptoResponse>>()
        val domainData = mock<List<DomainCrypto>>()
        val dbData = mock<List<DBCrypto>>()
        whenever(apiService.getTopCrypto(vsCurrency)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)
        whenever(dbDomainModelMapper.toDB(domainData)).thenReturn(dbData)
        // When
        repository.refreshTopCryptoList(vsCurrency)
        // Then
        verify(cryptoDAO).insert(dbData)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCryptoDetails() = runTest {
        // Given
        val cryptoId = "bitcoin"
        val apiResponse = mock<CryptoDetailsResponse>()
        val domainData = mock<DomainCryptoDetails>()
        whenever(apiService.getCryptoDetails(cryptoId)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)
        // When
        val result = repository.getCryptoDetails(cryptoId).first()
        // Then
        assertNotNull(result)
        assertEquals(domainData, result)
    }
}
