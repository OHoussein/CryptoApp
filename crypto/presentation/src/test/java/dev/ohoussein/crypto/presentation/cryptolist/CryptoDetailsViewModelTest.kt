@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.ohoussein.crypto.presentation.cryptolist

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.NavPath.CryptoDetailsPath
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsState
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
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

class CryptoDetailsViewModelTest : DescribeSpec({

    isolationMode = IsolationMode.InstancePerTest
    coroutineTestScope = true

    val testDispatcher = UnconfinedTestDispatcher()
    extension(TestCoroutineExtension(testDispatcher))

    val cryptoId = "bitcoin"

    val useCase = mock<GetCryptoDetails>()
    val uiMapper = mock<DomainModelMapper>()
    val errorMessage = "an error message"
    val errorMessageFormatter = mock<ErrorMessageFormatter> {
        on { invoke(any()) } doReturn errorMessage
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
        val data: DomainCryptoDetails = mock()
        val uiData: CryptoDetails = mock()

        whenever(uiMapper.convert(data)).thenReturn(uiData)
        whenever(useCase(cryptoId))
            .thenReturn(flowOf(data))

        describe("a success crypto details") {
            whenever(useCase.refresh(cryptoId)).doSuspendableAnswer { delay(1000) }

            describe("Screen opened") {
                beforeEach {
                    viewModel.dispatch(CryptoDetailsIntent.ScreenOpened)
                }

                it("the state should takes loading then success") {
                    viewModel.state.test {
                        awaitItem() shouldBe CryptoDetailsState(uiData, DataStatus.Loading)
                        testDispatcher.scheduler.advanceTimeBy(1001)
                        awaitItem() shouldBe CryptoDetailsState(uiData, DataStatus.Success)
                    }
                }
            }
        }

        describe("an error on getting crypto details") {
            beforeEach {
                testDispatcher.scheduler.advanceUntilIdle()
                whenever(useCase.refresh(cryptoId))
                    .doSuspendableAnswer {
                        delay(100)
                        throw Error()
                    }
            }

            describe("ScreenOpened") {
                beforeEach {
                    viewModel.dispatch(CryptoDetailsIntent.ScreenOpened)
                }

                it("the state should takes loading then error") {
                    viewModel.state.test {
                        awaitItem() shouldBe CryptoDetailsState(uiData, DataStatus.Loading)
                        testDispatcher.scheduler.advanceUntilIdle()
                        awaitItem() shouldBe CryptoDetailsState(uiData, DataStatus.Error(errorMessage))
                    }
                }
            }
        }
    }
})
