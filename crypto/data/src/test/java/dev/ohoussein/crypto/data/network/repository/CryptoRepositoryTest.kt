package dev.ohoussein.crypto.data.network.repository

import app.cash.turbine.test
import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.api.model.CryptoDetailsResponse
import dev.ohoussein.crypto.data.api.model.TopCryptoResponse
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CryptoRepositoryTest : BehaviorSpec({

    coroutineTestScope = true

    val apiService = mock<ApiCryptoService>()
    val apiDomainModelMapper = mock<ApiDomainModelMapper>()
    val dbDomainModelMapper = mock<DbDomainModelMapper>()
    val cryptoDAO = mock<CryptoDAO>()

    val currency = "USDT"
    val cryptoId = "bitcoin"

    val cryptoRepository: ICryptoRepository = CryptoRepository(
        service = apiService,
        cryptoDao = cryptoDAO,
        apiMapper = apiDomainModelMapper,
        dbMapper = dbDomainModelMapper,
        currency = currency,
    )

/*    given("a list of db crypto") {
        val dbList = listOf<DBCrypto>(mock(), mock())
        val domainData = listOf<DomainCrypto>(mock(), mock())
        whenever(cryptoDAO.getAll()).thenReturn(flowOf(dbList))
        whenever(dbDomainModelMapper.convertDBCrypto(dbList)).thenReturn(domainData)

        `when`("getTopCryptoList") {
            val result = cryptoRepository.getTopCryptoList(any()).first()

            then("it should get the data from the datasource") {
                result shouldBe domainData
            }
        }
    }*/

    given("a list of api crypto") {
        val apiResponse = listOf<TopCryptoResponse>(mock(), mock())
        val domainData = listOf<DomainCrypto>(mock(), mock())

        whenever(apiService.getTopCrypto(currency)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)

        `when`("refreshTopCryptoList") {
            val result = cryptoRepository.getTopCryptoList(CachePolicy.FRESH)

            then("it should get the data from the datasource") {
                result.test {
                    awaitItem() shouldBe CachedData.fresh(domainData)
                    awaitComplete()
                }
            }
        }
    }

    given("a crypto details") {
        val apiResponse = mock<CryptoDetailsResponse>()
        val domainData = mock<DomainCryptoDetails>()
        whenever(apiService.getCryptoDetails(cryptoId)).thenReturn(apiResponse)
        whenever(apiDomainModelMapper.convert(apiResponse)).thenReturn(domainData)

        `when`("getCryptoDetails") {
            val result = cryptoRepository.getCryptoDetails(cryptoId, CachePolicy.FRESH)

            then("it should get the data from the datasource") {
                result.test {
                    awaitItem() shouldBe CachedData.fresh(domainData)
                    awaitComplete()
                }
            }
        }
    }
})
