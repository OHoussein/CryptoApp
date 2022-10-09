package dev.ohoussein.cryptoapp.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetTopCryptoListTest : BehaviorSpec({

    val vsCurrency = "USD"
    val cryptoRepository = mock<ICryptoRepository>()
    val getTopCryptoList = GetTopCryptoList(cryptoRepository)

    given("a getTopCryptoList answer") {
        val data = mock<List<DomainCrypto>>()
        whenever(cryptoRepository.getTopCryptoList(vsCurrency)).thenReturn(flowOf(data))

        `when`("call the get from the use case") {
            getTopCryptoList.get(vsCurrency)

            then("it should calls the getTopCryptoList from the repository") {
                verify(cryptoRepository).getTopCryptoList(vsCurrency)
            }
        }
    }

    given("a success  refreshTopCryptoList") {
        whenever(cryptoRepository.refreshTopCryptoList(vsCurrency)).thenReturn(Unit)

        `when`("call the use refresh from the use case") {
            getTopCryptoList.refresh(vsCurrency)

            then("it should calls the getTopCryptoList from the repository") {
                verify(cryptoRepository).refreshTopCryptoList(vsCurrency)
            }
        }
    }
})
