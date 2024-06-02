package dev.ohoussein.cryptoapp.crypto.presentation.details

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakeGetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePercentFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePriceFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@ExperimentalCoroutinesApi
class CryptoDetailsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: FakeGetCryptoDetailsUseCase
    private lateinit var modelMapper: DomainModelMapper

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = FakeGetCryptoDetailsUseCase()
        modelMapper = DomainModelMapper(
            priceFormatter = FakePriceFormatter(),
            percentFormatter = FakePercentFormatter(),
            locale = Locale("USD", "en"),
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given a list of crypto When observe the state Then should set the state of the crypto list`() = runTest {
        val viewModel = CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
            cryptoId = "bitcoin",
        )

        viewModel.state.test {
            awaitItem().apply {
                assertNotNull(cryptoDetails)
                assertIs<DataStatus.Success>(status)
                cryptoDetails?.let {
                    assertEquals("bitcoin", it.base.id)
                    assertEquals("crypto bitcoin", it.base.name)
                    assertEquals("CR-BITCOIN", it.base.symbol)
                    assertEquals("SHA-256", it.hashingAlgorithm)
                    assertEquals("http://home-bitcoin.com", it.homePageUrl)
                }
            }
        }
    }

    @Test
    fun `Given an error then success When observe and refresh Then it should set the error then the success state`() =
        runTest {
            useCase.shouldThrowOnRefresh = true
            val viewModel = CryptoDetailsViewModel(
                useCase = useCase,
                modelMapper = modelMapper,
                cryptoId = "bitcoin",
            )

            viewModel.state.test {
                assertIs<DataStatus.Error>(awaitItem().status)

                useCase.shouldThrowOnRefresh = false
                viewModel.dispatch(CryptoDetailsEvents.Refresh)
                awaitItem().apply {
                    assertIs<DataStatus.Success>(status)
                    assertNotNull(cryptoDetails)
                }
            }
        }
}
