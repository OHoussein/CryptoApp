package dev.ohoussein.crypto.data.network.repository

import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.api.model.CryptoDetailsResponse
import dev.ohoussein.crypto.data.api.model.TopCryptoResponse
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CryptoRepositoryTest : BehaviorSpec({

    coroutineTestScope = true

    val apiService = mock<ApiCryptoService>()
    val apiDomainModelMapper = mock<ApiDomainModelMapper>()
    val dbDomainModelMapper = mock<DbDomainModelMapper>()
    val cryptoDAO = mock<CryptoDAO>()

    val vsCurrency = "USDT"
    val cryptoId = "bitcoin"

    val cryptoRepository: ICryptoRepository = CryptoRepository(
        service = apiService,
        cryptoDao = cryptoDAO,
        apiMapper = apiDomainModelMapper,
        dbMapper = dbDomainModelMapper,
    )

    given("a list of db crypto") {
        val dbList = listOf<DBCrypto>(mock(), mock())
        val domainData = listOf<DomainCrypto>(mock(), mock())
        whenever(cryptoDAO.getAll()).thenReturn(flowOf(dbList))
        whenever(dbDomainModelMapper.convertDBCrypto(dbList)).thenReturn(domainData)

        `when`("getTopCryptoList") {
            val result = cryptoRepository.getTopCryptoList(vsCurrency).first()

            then("it should get the data from the datasource") {
                result shouldBe domainData
            }
        }
    }

    given("a list of api crypto") {
        val apiResponse = listOf<TopCryptoResponse>(mock(), mock())
        val domainData = listOf<DomainCrypto>(mock(), mock())
        val dbData = listOf<DBCrypto>(mock(), mock())

        whenever(apiService.getTopCrypto(vsCurrency)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)
        whenever(dbDomainModelMapper.toDB(domainData)).thenReturn(dbData)

        `when`("refreshTopCryptoList") {
            cryptoRepository.refreshTopCryptoList(vsCurrency)

            then("it should insert the data in the database") {
                verify(cryptoDAO).insert(dbData)
            }
        }
    }

    given("a crypto details") {
        val apiResponse = mock<CryptoDetailsResponse>()
        val domainData = mock<DomainCryptoDetails>()
        whenever(apiService.getCryptoDetails(cryptoId)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)

        `when`("getCryptoDetails") {
            val result = cryptoRepository.getCryptoDetails(cryptoId).first()

            then("it should get the data from the datasource") {
                result shouldBe domainData
            }
        }
    }
})
