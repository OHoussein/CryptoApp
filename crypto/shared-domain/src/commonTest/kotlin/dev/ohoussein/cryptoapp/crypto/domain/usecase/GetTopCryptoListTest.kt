package dev.ohoussein.cryptoapp.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf

class GetTopCryptoListTest : BehaviorSpec({

    val cryptoRepository = mockk<ICryptoRepository>()
    val getTopCryptoList = GetTopCryptoList(cryptoRepository)

    given("a getTopCryptoList answer") {
        val data = mockk<List<DomainCrypto>>()
        every { cryptoRepository.getTopCryptoList() } returns flowOf(data)
        coJustRun { cryptoRepository.refreshTopCryptoList() }

        `when`("call the get from the use case") {
            getTopCryptoList()

            then("it should calls the getTopCryptoList from the repository") {
                verify { cryptoRepository.getTopCryptoList() }
            }
        }

        `when`("refresh") {
            getTopCryptoList.refresh()

            then("it should calls refreshTopCryptoList from cryptoRepository") {
                coVerify { cryptoRepository.refreshTopCryptoList() }
            }
        }
    }
})
