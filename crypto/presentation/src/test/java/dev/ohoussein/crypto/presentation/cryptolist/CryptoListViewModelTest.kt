package dev.ohoussein.crypto.presentation.cryptolist

import app.cash.turbine.test
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.reducer.CryptoListIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoListState
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import dev.ohoussein.cryptoapp.common.resource.DataStatus
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class CryptoListViewModelTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest
    coroutineTestScope = true

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
        val data: List<DomainCrypto> = mock()
        val uiData: List<Crypto> = mock()

        whenever(uiMapper.convert(data)).thenReturn(uiData)

        describe("Success on fresh data") {
            whenever(useCase())
                .thenReturn(flowOf(data))
            whenever(useCase.refresh())
                .doSuspendableAnswer { delay(1000) }

            describe("onScreenOpened") {
                beforeEach {
                    cryptoListViewModel.dispatch(CryptoListIntent.ScreenOpened)
                }

                it("should have the data and loading status then success status") {
                    cryptoListViewModel.state.test {
                        awaitItem() shouldBe CryptoListState(uiData, DataStatus.Loading)
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe CryptoListState(uiData, DataStatus.Success)
                    }
                }

                describe("a success onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 success state") {
                        cryptoListViewModel.state.test {
                            awaitItem() shouldBe CryptoListState(uiData, DataStatus.Loading)
                            testDispatcher.scheduler.advanceUntilIdle()
                            awaitItem() shouldBe CryptoListState(uiData, DataStatus.Success)
                        }
                    }
                }

                describe("an error onRefresh") {
                    beforeEach {
                        testDispatcher.scheduler.advanceUntilIdle()
                        whenever(useCase.refresh())
                            .doSuspendableAnswer {
                                delay(1000)
                                throw Error()
                            }
                        cryptoListViewModel.dispatch(CryptoListIntent.Refresh)
                    }

                    it("should get 1 loading state and 1 error state") {
                        cryptoListViewModel.state.test {
                            awaitItem() shouldBe CryptoListState(uiData, DataStatus.Loading)
                            testDispatcher.scheduler.advanceTimeBy(1001)
                            awaitItem() shouldBe CryptoListState(uiData, DataStatus.Error(errorMessage))
                        }
                    }
                }
            }
        }

        describe("Error on fresh data") {
            whenever(useCase())
                .thenReturn(flowOf(data))
            whenever(useCase.refresh())
                .doSuspendableAnswer {
                    delay(1000)
                    throw Error()
                }

            describe("onScreenOpened") {
                beforeEach {
                    cryptoListViewModel.dispatch(CryptoListIntent.ScreenOpened)
                }

                it("should have the data and loading status then error") {
                    cryptoListViewModel.state.test {
                        awaitItem() shouldBe CryptoListState(uiData, DataStatus.Loading)
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe CryptoListState(uiData, DataStatus.Error(errorMessage))
                    }
                }
            }
        }
    }
})
