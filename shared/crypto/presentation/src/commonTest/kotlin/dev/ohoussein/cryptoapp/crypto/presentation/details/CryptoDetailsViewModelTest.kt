package dev.ohoussein.cryptoapp.crypto.presentation.details

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.model.defaultLocale
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakeGetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePercentFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePriceFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakeRouter
import dev.ohoussein.cryptoapp.crypto.presentation.graph.GraphGridGenerator
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
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
    fun `Given a crypto When observe the state Then should set the state of the crypto details`() = runTest {
        val viewModel = CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
            router = FakeRouter(),
            graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
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
                assertTrue(graphState.graphPrices.isNotEmpty())
                assertEquals(GraphInterval.entries.size, graphState.allIntervals.size)
            }
        }
    }

    @Test
    fun `Given a crypto When SourceCodeClicked Then it should open the url`() = runTest {
        val router = FakeRouter()
        val viewModel = CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
            router = router,
            graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
            cryptoId = "bitcoin",
        )

        viewModel.dispatch(CryptoDetailsEvents.SourceCodeClicked)

        assertEquals(listOf("http://repo-bitcoin.com"), router.openedUrls)
    }

    @Test
    fun `Given a crypto When HomePageClicked Then it should open the url`() = runTest {
        val router = FakeRouter()
        val viewModel = CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
            router = router,
            graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
            cryptoId = "bitcoin",
        )

        viewModel.dispatch(CryptoDetailsEvents.HomePageClicked)

        assertEquals(listOf("http://home-bitcoin.com"), router.openedUrls)
    }

    @Test
    fun `Given a crypto When BlockchainSiteClicked Then it should open the url`() = runTest {
        val router = FakeRouter()
        val viewModel = CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
            router = router,
            graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
            cryptoId = "bitcoin",
        )

        viewModel.dispatch(CryptoDetailsEvents.BlockchainSiteClicked)

        assertEquals(listOf("http://blockchain-bitcoin.com"), router.openedUrls)
    }

    @Test
    fun `Given an error then success When observe and refresh Then it should set the error then the success state`() =
        runTest {
            useCase.shouldThrowOnRefresh = true
            val viewModel = CryptoDetailsViewModel(
                useCase = useCase,
                modelMapper = modelMapper,
                router = FakeRouter(),
                graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
                cryptoId = "bitcoin",
            )

            with(viewModel.state.value) {
                assertIs<DataStatus.Error>(status)
            }
            useCase.shouldThrowOnRefresh = false
            viewModel.dispatch(CryptoDetailsEvents.Refresh)
            with(viewModel.state.value) {
                assertIs<DataStatus.Success>(status)
                assertNotNull(cryptoDetails)
            }
        }

    @Test
    fun `Given a crypto When SelectInterval Then it should set the new interval`() = runTest {
        val router = FakeRouter()
        val viewModel = CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
            router = router,
            graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
            cryptoId = "bitcoin",
        )

        viewModel.dispatch(CryptoDetailsEvents.SelectInterval(GraphInterval.INTERVAL_1_MONTH))

        with(viewModel.state.value) {
            assertEquals(GraphInterval.INTERVAL_1_MONTH, graphState.selectedInterval)
            assertEquals(30, graphState.graphPrices.size)
        }
    }

    @Test
    fun `Given a cached historical prices When Select the same Interval twice Then it should set the value from the cache`() =
        runTest {
            val router = FakeRouter()
            val viewModel = CryptoDetailsViewModel(
                useCase = useCase,
                modelMapper = modelMapper,
                router = router,
                graphGridGenerator = GraphGridGenerator(FakePriceFormatter(), defaultLocale),
                cryptoId = "bitcoin",
            )

            viewModel.dispatch(CryptoDetailsEvents.SelectInterval(GraphInterval.INTERVAL_1_MONTH))
            viewModel.dispatch(CryptoDetailsEvents.SelectInterval(GraphInterval.INTERVAL_1_DAY))
            useCase.shouldThrowOnRefresh = true
            viewModel.dispatch(CryptoDetailsEvents.SelectInterval(GraphInterval.INTERVAL_1_MONTH))

            with(viewModel.state.value) {
                assertEquals(GraphInterval.INTERVAL_1_MONTH, graphState.selectedInterval)
                assertEquals(30, graphState.graphPrices.size)
            }
        }
}
