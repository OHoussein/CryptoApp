package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf

class GetCryptoDetailsTest : BehaviorSpec({

    val cryptoId = "bitcoin"
    val cryptoRepository = mockk<ICryptoRepository>()
    val getCryptoDetails = GetCryptoDetails(cryptoRepository)

    given("a getCryptoDetails answer") {
        every { cryptoRepository.getCryptoDetails(cryptoId) } returns flowOf()
        coJustRun { cryptoRepository.refreshCryptoDetails(cryptoId) }

        `when`("call the use case") {
            getCryptoDetails(cryptoId)

            then("it should calls the getCryptoDetails from the repository") {
                verify { cryptoRepository.getCryptoDetails(cryptoId) }
            }
        }

        `when`("refresh") {
            getCryptoDetails.refresh(cryptoId)

            then("it should calls refreshTopCryptoList from cryptoRepository") {
                coVerify { cryptoRepository.refreshCryptoDetails(cryptoId) }
            }
        }
    }
})
