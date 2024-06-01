package dev.ohoussein.cryptoapp.crypto.presentation.list

import app.cash.turbine.test
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakeGetTopCryptoListUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePercentFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePriceFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import kotlin.test.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
class CryptoListViewModelTest {
    private lateinit var useCase: FakeGetTopCryptoListUseCase
    private lateinit var modelMapper: DomainModelMapper

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        useCase = FakeGetTopCryptoListUseCase()
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
        val viewModel = CryptoListViewModel(
            useCase = useCase,
            modelMapper = modelMapper,
        )

        viewModel.state.test {
            awaitItem().apply {
                assertNotNull(cryptoList)
                assertEquals(5, cryptoList!!.size)
                assertIs<DataStatus.Success>(status)
                val firstCrypto = cryptoList!!.first()
                assertEquals("70.0 USD", firstCrypto.price.labelValue.label)
                assertEquals(70.0, firstCrypto.price.labelValue.value)
                assertEquals(-2.0, firstCrypto.priceChangePercentIn24h?.value)
                assertEquals("1", firstCrypto.info.id)
                assertEquals("crypto 1", firstCrypto.info.name)
                assertEquals("CR-1", firstCrypto.info.symbol)
            }
        }
    }

    @Test
    fun `Given an error then success When observe and refresh Then it should set the error then the success state`() =
        runTest {
            useCase.shouldThrowOnRefresh = true
            val viewModel = CryptoListViewModel(
                useCase = useCase,
                modelMapper = modelMapper,
            )

            viewModel.state.test {
                assertIs<DataStatus.Error>(awaitItem().status)

                useCase.shouldThrowOnRefresh = false
                viewModel.dispatch(CryptoListEvents.Refresh)

                awaitItem().apply {
                    assertIs<DataStatus.Success>(status)
                    assertNotNull(cryptoList)
                    assertEquals(5, cryptoList!!.size)
                }
            }
        }
}
