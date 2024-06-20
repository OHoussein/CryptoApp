package dev.ohoussein.cryptoapp.crypto.presentation.details

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.presentation.fake.*
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
    private lateinit var router: FakeRouter

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = FakeGetCryptoDetailsUseCase()
        router = FakeRouter()
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
        val viewModel = viewModel()

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
    fun `Given a crypto When SourceCodeClicked Then it should open the url`() = runTest {
        val viewModel = viewModel()

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
            cryptoId = "bitcoin",
        )

        viewModel.dispatch(CryptoDetailsEvents.HomePageClicked)

        assertEquals(listOf("http://home-bitcoin.com"), router.openedUrls)
    }

    @Test
    fun `Given a crypto When BlockchainSiteClicked Then it should open the url`() = runTest {
        val viewModel = viewModel()

        viewModel.dispatch(CryptoDetailsEvents.BlockchainSiteClicked)

        assertEquals(listOf("http://blockchain-bitcoin.com"), router.openedUrls)
    }

    @Test
    fun `Given an error then success When observe and refresh Then it should set the error then the success state`() =
        runTest {
            useCase.shouldThrowOnRefresh = true
            val viewModel = viewModel()

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

    private fun viewModel() = CryptoDetailsViewModel(
        useCase = useCase,
        modelMapper = modelMapper,
        router = router,
        cryptoId = "bitcoin",
    )
}
