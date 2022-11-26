package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
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
        whenever(cryptoRepository.getCryptoDetails(cryptoId)).thenReturn(flowOf())

        `when`("call the use case") {
            getCryptoDetails(cryptoId)

            then("it should calls the getCryptoDetails from the repository") {
                verify(cryptoRepository).getCryptoDetails(cryptoId)
            }
        }

        `when`("refresh") {
            getCryptoDetails.refresh(cryptoId)

            then("it should calls refreshTopCryptoList from cryptoRepository") {
                verify(cryptoRepository).refreshCryptoDetails(cryptoId)
            }
        }
    }
})
