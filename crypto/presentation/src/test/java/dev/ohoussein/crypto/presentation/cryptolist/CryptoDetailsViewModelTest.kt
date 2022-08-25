@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.ohoussein.crypto.presentation.cryptolist

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dev.ohoussein.core.test.coroutine.flowOfError
import dev.ohoussein.core.test.coroutine.flowOfWithDelays
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.NavPath.CryptoDetailsPath
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsState
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.common.resource.Resource
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

class CryptoDetailsViewModelTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest

    val testDispatcher = UnconfinedTestDispatcher()
    extension(TestCoroutineExtension(testDispatcher))

    val cryptoId = "bitcoin"

    val useCase = mock<GetCryptoDetails>()
    val uiMapper = mock<DomainModelMapper>()
    val errorMessage = "an error message"
    val errorMessageFormatter = mock<ErrorMessageFormatter> {
        on { invoke(any<IOException>()) } doReturn errorMessage
    }

    val viewModel by lazy {
        CryptoDetailsViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    CryptoDetailsPath.ARG_CRYPTO_ID to cryptoId
                )
            ),
            useCase = useCase,
            modelMapper = uiMapper,
            errorMessageFormatter = errorMessageFormatter,
        )
    }

    describe("mocked data") {
        val domainCryptoDetails: DomainCryptoDetails = mock(name = "fresh")
        val cryptoDetails: CryptoDetails = mock(name = "fresh")

        whenever(uiMapper.convert(domainCryptoDetails)).thenReturn(cryptoDetails)

        describe("a success crypto details") {
            whenever(useCase(cryptoId, CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOfWithDelays(CachedData.fresh(domainCryptoDetails)))

            describe("Screen opened") {
                beforeEach {
                    viewModel.dispatch(CryptoDetailsIntent.ScreenOpened)
                }

                it("the state should takes loading then success") {
                    viewModel.state.test {
                        awaitItem() shouldBe CryptoDetailsState(Resource.loading())
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem() shouldBe CryptoDetailsState(Resource.success(cryptoDetails))
                    }
                }
            }
        }

        describe("an error on getting crypto details") {
            whenever(useCase(cryptoId, CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOfError(delayMs = 300))

            describe("ScreenOpened") {
                beforeEach {
                    viewModel.dispatch(CryptoDetailsIntent.ScreenOpened)
                }

                it("the state should takes loading then error") {
                    viewModel.state.test {
                        awaitItem() shouldBe CryptoDetailsState(Resource.loading())
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem() shouldBe CryptoDetailsState(Resource.error(errorMessage))
                    }
                }

                describe("a success on crypto details") {

                    whenever(useCase(cryptoId, CachePolicy.FRESH))
                        .thenReturn(flowOfWithDelays(CachedData.fresh(domainCryptoDetails)))

                    describe("Refresh") {
                        beforeEach {
                            testDispatcher.scheduler.advanceUntilIdle()
                            viewModel.dispatch(CryptoDetailsIntent.Refresh)
                        }

                        it("the state should takes loading then success") {
                            viewModel.state.test() {
                                awaitItem() shouldBe CryptoDetailsState(Resource.loading())
                                testDispatcher.scheduler.advanceTimeBy(301)
                                awaitItem() shouldBe CryptoDetailsState(Resource.success(cryptoDetails))
                            }
                        }
                    }
                }
            }
        }
    }
})
