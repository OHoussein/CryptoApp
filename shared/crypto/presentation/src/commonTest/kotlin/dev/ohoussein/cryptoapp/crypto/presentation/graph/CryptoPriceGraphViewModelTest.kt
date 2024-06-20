package dev.ohoussein.cryptoapp.crypto.presentation.graph

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.model.defaultLocale
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakeGetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePercentFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePriceFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@ExperimentalCoroutinesApi
class CryptoPriceGraphViewModelTest {
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
    fun `Given a crypto When observe the state Then should set the state`() = runTest {
        val viewModel = viewModel()

        viewModel.state.test {
            awaitItem().apply {
                assertTrue(graphPrices.isNotEmpty())
                assertEquals(GraphInterval.entries.size, allIntervals.size)
            }
        }
    }

    @Test
    fun `Given a crypto When SelectInterval Then it should set the new interval`() = runTest {
        val viewModel = viewModel()

        viewModel.dispatch(CryptoPriceGraphEvents.SelectInterval(GraphInterval.INTERVAL_1_MONTH))

        with(viewModel.state.value) {
            assertEquals(GraphInterval.INTERVAL_1_MONTH, selectedInterval)
            assertEquals(30, graphPrices.size)
        }
    }

    @Test
    fun `Given a cached historical prices When Select the same Interval twice Then it should set the value from the cache`() =
        runTest {
            val viewModel = viewModel()

            viewModel.dispatch(CryptoPriceGraphEvents.SelectInterval(GraphInterval.INTERVAL_1_MONTH))
            viewModel.dispatch(CryptoPriceGraphEvents.SelectInterval(GraphInterval.INTERVAL_1_DAY))
            useCase.shouldThrowOnRefresh = true
            viewModel.dispatch(CryptoPriceGraphEvents.SelectInterval(GraphInterval.INTERVAL_1_MONTH))

            with(viewModel.state.value) {
                assertEquals(GraphInterval.INTERVAL_1_MONTH, selectedInterval)
                assertEquals(30, graphPrices.size)
            }
        }

    private fun viewModel() = CryptoPriceGraphViewModel(
        useCase = useCase,
        modelMapper = modelMapper,
        graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
        cryptoId = "bitcoin",
    )
}
