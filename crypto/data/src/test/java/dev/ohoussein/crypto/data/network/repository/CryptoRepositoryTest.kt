package dev.ohoussein.crypto.data.network.repository

import app.cash.turbine.test
import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.api.model.CryptoDetailsResponse
import dev.ohoussein.crypto.data.api.model.TopCryptoResponse
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.model.defaultLocale
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCryptoDetails
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.any
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CryptoRepositoryTest : BehaviorSpec({

    coroutineTestScope = true
    isolationMode = IsolationMode.InstancePerTest

    val apiService = mock<ApiCryptoService>()
    val apiDomainModelMapper = mock<ApiDomainModelMapper>()
    val dbDomainModelMapper = mock<DbDomainModelMapper>()
    val cryptoDAO = mock<CryptoDAO>()

    val cryptoId = "bitcoin"

    val cryptoRepository: ICryptoRepository = CryptoRepository(
        service = apiService,
        cryptoDao = cryptoDAO,
        apiMapper = apiDomainModelMapper,
        dbMapper = dbDomainModelMapper,
        locale = defaultLocale,
    )

    given("a list crypto from database") {
        val dbCryptoList = listOf<DBCrypto>(mock(), mock())
        val domainData = listOf<DomainCrypto>(mock(), mock())

        whenever(cryptoDAO.getAll()).thenReturn(flowOf(dbCryptoList))
        whenever(dbDomainModelMapper.convertDBCrypto(dbCryptoList)).thenReturn(domainData)

        `when`("getTopCryptoList") {
            val result = cryptoRepository.getTopCryptoList()

            then("it should get the data from the database") {
                result.test {
                    awaitItem() shouldBe domainData
                    awaitComplete()
                }
            }
        }
    }

    given("list of crypto from api") {
        val cryptoList = listOf<TopCryptoResponse>(mock(), mock())
        val domainData = listOf<DomainCrypto>(mock(), mock())
        val dbCryptoList = mock<List<DBCrypto>>()

        whenever(apiService.getTopCrypto(any())).thenReturn(cryptoList)
        whenever(apiDomainModelMapper.convert(cryptoList)).thenReturn(domainData)
        whenever(dbDomainModelMapper.toDB(domainData)).thenReturn(dbCryptoList)
        whenever(cryptoDAO.replace(any())).thenReturn(listOf())
        whenever(cryptoDAO.getAll()).thenReturn(flowOf(dbCryptoList))

        `when`("refreshTopCryptoList") {
            cryptoRepository.refreshTopCryptoList()

            then("it should get data from the API") {
                verify(apiService).getTopCrypto(any())
            }

            then("it should insert the data in the database") {
                verify(cryptoDAO).replace(dbCryptoList)
            }
        }
    }

    given("a crypto details from database") {
        val dbCrypto = mock<DBCryptoDetails>()
        val domainData = mock<DomainCryptoDetails>()
        whenever(cryptoDAO.getCryptoDetails(cryptoId)).thenReturn(flowOf(dbCrypto))
        whenever(dbDomainModelMapper.toDomain(dbCrypto)).thenReturn(domainData)

        `when`("getCryptoDetails") {
            val result = cryptoRepository.getCryptoDetails(cryptoId)

            then("it should get the data from the datasource") {
                result.test {
                    awaitItem() shouldBe domainData
                    awaitComplete()
                }
            }
        }
    }

    given("a crypto details from api") {
        val apiResponse = mock<CryptoDetailsResponse>()
        val domainData = mock<DomainCryptoDetails>()
        val dbCrypto = mock<DBCryptoDetails>()
        whenever(apiService.getCryptoDetails(cryptoId)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)
        whenever(dbDomainModelMapper.toDB(domainData)).thenReturn(dbCrypto)
        whenever(cryptoDAO.getCryptoDetails(cryptoId)).thenReturn(flowOf(dbCrypto))

        `when`("getCryptoDetails") {
            cryptoRepository.refreshCryptoDetails(cryptoId)

            then("it should get data from the API") {
                verify(apiService).getCryptoDetails(cryptoId)
            }

            then("it should insert the data in the database") {
                verify(cryptoDAO).insert(dbCrypto)
            }
        }
    }
})
