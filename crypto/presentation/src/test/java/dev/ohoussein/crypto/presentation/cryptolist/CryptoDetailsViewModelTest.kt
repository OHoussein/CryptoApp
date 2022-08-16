@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.ohoussein.crypto.presentation.cryptolist

import dev.ohoussein.core.test.extension.InstantTaskExecutorExtension
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.core.test.livedata.mockedObserverOf
import dev.ohoussein.core.test.livedata.verifyStates
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.common.resource.Resource
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

class CryptoDetailsViewModelTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest

    extension(TestCoroutineExtension())
    extension(InstantTaskExecutorExtension())

    val cryptoId = "bitcoin"

    val useCase = mock<GetCryptoDetails>()
    val uiMapper = mock<DomainModelMapper>()
    val stateObserver = mockedObserverOf<Resource<CryptoDetails>>()
    val errorMessage = "an error message"
    val errorMessageFormatter = mock<ErrorMessageFormatter> {
        on { map(any()) } doReturn errorMessage
    }

    val viewModel by lazy {
        CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = uiMapper,
            errorMessageFormatter = errorMessageFormatter,
        ).apply {
            cryptoDetails.observeForever(stateObserver)
        }
    }

    describe("mocked data") {
        val domainCryptoDetails: DomainCryptoDetails = mock(name = "fresh")
        val cryptoDetails: CryptoDetails = mock(name = "fresh")

        whenever(uiMapper.convert(domainCryptoDetails)).thenReturn(cryptoDetails)

        describe("a success crypto details") {
            whenever(useCase(cryptoId, CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOf(CachedData.fresh(domainCryptoDetails)))

            describe("loan") {
                viewModel.load(cryptoId)

                it("the state should takes loading then success") {
                    stateObserver.verifyStates(
                        Resource.loading(),
                        Resource.success(cryptoDetails)
                    )
                }
            }
        }

        describe("an error on getting crypto details") {
            val exception = IOException("")
            whenever(useCase(cryptoId, CachePolicy.CACHE_THEN_FRESH)).thenReturn(flow { throw exception })

            describe("load") {
                viewModel.load(cryptoId)

                it("the state should takes loading then error") {
                    stateObserver.verifyStates(
                        Resource.loading(),
                        Resource.error(errorMessage)
                    )
                }

                describe("a sucess on crypto details") {
                    whenever(useCase(cryptoId, CachePolicy.CACHE_THEN_FRESH))
                        .thenReturn(flowOf(CachedData.fresh(domainCryptoDetails)))

                    describe("loan") {
                        viewModel.load(cryptoId)

                        it("the state should takes loading then success") {
                            stateObserver.verifyStates(
                                Resource.loading(),
                                Resource.success(cryptoDetails)
                            )
                        }
                    }
                }
            }
        }
    }
})
