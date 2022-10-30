package dev.ohoussein.crypto.presentation.cryptolist

import app.cash.turbine.test
import dev.ohoussein.core.test.coroutine.flowOfError
import dev.ohoussein.core.test.coroutine.flowOfWithDelays
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.reducer.CryptoListIntent
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.common.resource.Resource
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CryptoListViewModelTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest

    val testDispatcher = UnconfinedTestDispatcher()
    extension(TestCoroutineExtension(testDispatcher))

    val useCase = mock<GetTopCryptoList>()
    val uiMapper = mock<DomainModelMapper>()
    val errorMessage = "an error message"
    val errorMessageFormatter = mock<ErrorMessageFormatter> {
        on { invoke(any()) } doReturn errorMessage
    }

    val cryptoListViewModel by lazy {
        CryptoListViewModel(
            useCase = useCase,
            modelMapper = uiMapper,
            errorMessageFormatter = errorMessageFormatter,
        )
    }

    describe("mocked data") {
        val cachedData: List<DomainCrypto> = mock(name = "cached")
        val cachedUiData: List<Crypto> = mock(name = "cached")
        val freshData: List<DomainCrypto> = mock(name = "fresh")
        val freshUiData: List<Crypto> = mock(name = "fresh")

        val freshRefreshData: List<DomainCrypto> = mock(name = "fresh_refresh")
        val freshRefreshUiData: List<Crypto> = mock(name = "fresh_refresh")

        whenever(uiMapper.convert(cachedData)).thenReturn(cachedUiData)
        whenever(uiMapper.convert(freshData)).thenReturn(freshUiData)
        whenever(uiMapper.convert(freshRefreshData)).thenReturn(freshRefreshUiData)

        describe("cached crypto data & fresh crypto data") {
            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOfWithDelays(CachedData.cached(cachedData, true), CachedData.fresh(freshData)))

            describe("onScreenOpened") {
                beforeEach {
                    cryptoListViewModel.dispatch(CryptoListIntent.ScreenOpened)
                }

                it("should get 1 loading state and 1 success state") {
                    cryptoListViewModel.state.test {
                        awaitItem().cryptoList shouldBe Resource.loading()
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem().cryptoList shouldBe Resource.loading(cachedUiData)
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem().cryptoList shouldBe Resource.success(freshUiData)
                    }
                }

                describe("a success onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOfWithDelays(CachedData.fresh(freshRefreshData)))

                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 success state with the new refreshed data") {
                        cryptoListViewModel.state.test {
                            awaitItem().cryptoList shouldBe Resource.loading(freshUiData)
                            testDispatcher.scheduler.advanceTimeBy(301)
                            awaitItem().cryptoList shouldBe Resource.success(freshRefreshUiData)
                        }
                    }
                }

                describe("an error onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOfError(delayMs = 300))
                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 error state with the existing fresh data") {
                        cryptoListViewModel.state.test {
                            awaitItem().cryptoList shouldBe Resource.loading(freshUiData)
                            testDispatcher.scheduler.advanceTimeBy(301)
                            awaitItem().cryptoList shouldBe Resource.error(errorMessage, freshUiData)
                        }
                    }
                }
            }
        }

        describe("cached crypto data & error fresh crypto data") {
            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(
                    flow {
                        delay(300)
                        emit(CachedData.cached(cachedData, true))
                        delay(300)
                        throw IOException("")
                    }
                )

            describe("onScreenOpened") {
                cryptoListViewModel.dispatch(CryptoListIntent.ScreenOpened)

                it("should get loading and error states with the cached data") {
                    cryptoListViewModel.state.test {
                        awaitItem().cryptoList shouldBe Resource.loading()
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem().cryptoList shouldBe Resource.loading(cachedUiData)
                        testDispatcher.scheduler.advanceTimeBy(300)
                        awaitItem().cryptoList shouldBe Resource.error(errorMessage, cachedUiData)
                    }
                }

                describe("a success onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOfWithDelays(CachedData.fresh(freshRefreshData)))
                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 success state with the new refreshed data") {
                        cryptoListViewModel.state.test {
                            testDispatcher.scheduler.advanceTimeBy(301)
                            awaitItem().cryptoList shouldBe Resource.loading(cachedUiData)
                            testDispatcher.scheduler.advanceTimeBy(300)
                            awaitItem().cryptoList shouldBe Resource.success(freshRefreshUiData)
                        }
                    }
                }

                describe("an error onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOfError(delayMs = 300))
                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 error state with the existing fresh data") {
                        cryptoListViewModel.state.test {
                            awaitItem().cryptoList shouldBe Resource.loading(cachedUiData)
                            testDispatcher.scheduler.advanceTimeBy(301)
                            awaitItem().cryptoList shouldBe Resource.error(errorMessage, cachedUiData)
                        }
                    }
                }
            }
        }

        describe("no cached crypto data & fresh crypto data") {
            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOfWithDelays(CachedData.fresh(freshData)))

            describe("onScreenOpened") {
                cryptoListViewModel.dispatch(CryptoListIntent.ScreenOpened)

                it("should get 1 loading state and 1 success state") {
                    cryptoListViewModel.state.test {
                        awaitItem().cryptoList shouldBe Resource.loading()
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem().cryptoList shouldBe Resource.success(freshUiData)
                    }
                }
            }
        }

        describe("no cached crypto data & error on fresh crypto data") {
            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOfError(delayMs = 300))

            describe("onScreenOpened") {
                beforeEach {
                    cryptoListViewModel.dispatch(CryptoListIntent.ScreenOpened)
                }

                it("should get 1 loading state and 1 error state") {
                    cryptoListViewModel.state.test {
                        awaitItem().cryptoList shouldBe Resource.loading()
                        testDispatcher.scheduler.advanceTimeBy(301)
                        awaitItem().cryptoList shouldBe Resource.error(errorMessage)
                    }
                }

                describe("onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOfError(delayMs = 300))

                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 error state") {
                        cryptoListViewModel.state.test {
                            awaitItem().cryptoList shouldBe Resource.loading()
                            testDispatcher.scheduler.advanceTimeBy(301)
                            awaitItem().cryptoList shouldBe Resource.error(errorMessage)
                        }
                    }
                }
            }
        }
    }
})
