package dev.ohoussein.crypto.presentation.cryptolist

import dev.ohoussein.core.test.extension.InstantTaskExecutorExtension
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.core.test.livedata.mockedObserverOf
import dev.ohoussein.core.test.livedata.verifyStates
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.viewmodel.HomeViewModel
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.common.resource.Resource
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class HomeViewModelTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest

    extension(TestCoroutineExtension())
    extension(InstantTaskExecutorExtension())

    val useCase = mock<GetTopCryptoList>()
    val uiMapper = mock<DomainModelMapper>()
    val stateObserver = mockedObserverOf<Resource<List<Crypto>>>()

    val homeViewModel by lazy {
        HomeViewModel(
            useCase = useCase,
            modelMapper = uiMapper,
        ).apply {
            topCryptoList.observeForever(stateObserver)
        }
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
                .thenReturn(flowOf(CachedData.cached(cachedData, true), CachedData.fresh(freshData)))

            describe("onScreenOpened") {
                beforeEach {
                    homeViewModel.onScreenOpened()
                }

                it("should get 1 loading state and 1 success state") {
                    stateObserver.verifyStates(
                        Resource.loading(),
                        Resource.loading(cachedUiData),
                        Resource.success(freshUiData),
                    )
                }

                describe("a success onRefresh") {
                    beforeEach {
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOf(CachedData.fresh(freshRefreshData)))
                        homeViewModel.onRefresh()
                    }

                    it("should get 1 loading state and 1 success state with the new refreshed data") {
                        stateObserver.verifyStates(
                            Resource.loading(freshUiData),
                            Resource.success(freshRefreshUiData),
                        )
                    }
                }

                describe("an error onRefresh") {
                    val exception = IOException()
                    beforeEach {
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(
                                flow {
                                    throw exception
                                }
                            )
                        homeViewModel.onRefresh()
                    }

                    it("should get 1 loading state and 1 error state with the existing fresh data") {
                        stateObserver.verifyStates(
                            Resource.loading(freshUiData),
                            Resource.error(exception, freshUiData),
                        )
                    }
                }
            }
        }

        describe("cached crypto data & error fresh crypto data") {
            val error = IOException("")

            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(
                    flow {
                        emit(CachedData.cached(cachedData, true))
                        throw error
                    }
                )

            describe("onScreenOpened") {
                homeViewModel.onScreenOpened()

                it("should get loading and error states with the cached data") {
                    stateObserver.verifyStates(
                        Resource.loading(),
                        Resource.loading(cachedUiData),
                        Resource.error(error, cachedUiData),
                    )
                }

                describe("a success onRefresh") {
                    beforeEach {
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(flowOf(CachedData.fresh(freshRefreshData)))
                        homeViewModel.onRefresh()
                    }

                    it("should get 1 loading state and 1 success state with the new refreshed data") {
                        stateObserver.verifyStates(
                            Resource.loading(cachedUiData),
                            Resource.success(freshRefreshUiData),
                        )
                    }
                }

                describe("an error onRefresh") {
                    val exception = IOException()
                    beforeEach {
                        whenever(useCase.get(CachePolicy.FRESH))
                            .thenReturn(
                                flow {
                                    throw exception
                                }
                            )
                        homeViewModel.onRefresh()
                    }

                    it("should get 1 loading state and 1 error state with the existing fresh data") {
                        stateObserver.verifyStates(
                            Resource.loading(cachedUiData),
                            Resource.error(exception, cachedUiData),
                        )
                    }
                }
            }
        }

        describe("no cached crypto data & fresh crypto data") {
            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flowOf(CachedData.fresh(freshData)))

            describe("onScreenOpened") {
                homeViewModel.onScreenOpened()

                it("should get 1 loading state and 1 success state") {
                    stateObserver.verifyStates(
                        Resource.loading(),
                        Resource.success(freshUiData),
                    )
                }
            }
        }

        describe("no cached crypto data & error on fresh crypto data") {
            val error = IOException("")

            whenever(useCase.get(CachePolicy.CACHE_THEN_FRESH))
                .thenReturn(flow { throw error })

            describe("onScreenOpened") {
                homeViewModel.onScreenOpened()

                it("should get 1 loading state and 1 success state") {
                    stateObserver.verifyStates(
                        Resource.loading(),
                        Resource.error(error),
                    )
                }

                describe("onRefresh") {
                    homeViewModel.onRefresh()

                    it("should get 1 loading state and 1 success state") {
                        stateObserver.verifyStates(
                            Resource.loading(),
                            Resource.error(error),
                            Resource.loading(),
                        )
                    }
                }
            }
        }
    }
})
