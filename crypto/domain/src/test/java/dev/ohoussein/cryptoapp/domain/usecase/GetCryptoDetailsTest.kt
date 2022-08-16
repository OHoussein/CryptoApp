package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetCryptoDetailsTest : BehaviorSpec({

    val cryptoId = "bitcoin"
    val cryptoRepository = mock<ICryptoRepository>()
    val getCryptoDetails = GetCryptoDetails(cryptoRepository)

    given("a getCryptoDetails answer") {
        whenever(cryptoRepository.getCryptoDetails(cryptoId, CachePolicy.CACHE_THEN_FRESH)).thenReturn(flowOf())

        `when`("call the use case") {
            getCryptoDetails(cryptoId, CachePolicy.CACHE_THEN_FRESH)

            then("it should calls the getCryptoDetails from the repository") {
                verify(cryptoRepository).getCryptoDetails(cryptoId, CachePolicy.CACHE_THEN_FRESH)
            }
        }
    }
})
