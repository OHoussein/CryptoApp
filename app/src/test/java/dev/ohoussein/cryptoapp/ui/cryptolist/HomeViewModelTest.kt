package dev.ohoussein.cryptoapp.ui.cryptolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.atLeast
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.commonTest.rule.TestCoroutineRule
import dev.ohoussein.cryptoapp.core.TestCoroutineContextProvider
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.mock.TestDataFactory
import dev.ohoussein.cryptoapp.ui.core.UiCoreModule
import dev.ohoussein.cryptoapp.ui.core.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.Resource
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.times
import java.io.IOException
import java.text.NumberFormat
import java.util.Locale

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var tested: HomeViewModel
    private lateinit var useCase: GetTopCryptoList

    private val uiMapper = DomainModelMapper(
        UiCoreModule.providePriceFormatter(),
        UiCoreModule.providePercentFormatter(),
    )
    private val mockCryptoListObserver = mock<Observer<Resource<List<Crypto>>>>()

    @Before
    fun setup() {
        useCase = mock()
        tested = HomeViewModel(
            TestCoroutineContextProvider(),
            useCase,
            uiMapper,
        )
    }

    @Test
    fun `should load top crypto list`() {
        //Given
        tested.topCryptoList.observeForever(mockCryptoListObserver)
        givenListOfCrypto { data ->
            //When
            tested.load()
            //Then
            verify(mockCryptoListObserver).onChanged(Resource.loading())
            verify(mockCryptoListObserver).onChanged(Resource.success(data))
        }
    }

    @Test
    fun `should get latest data when refreshing`() {

        //Given
        tested.topCryptoList.observeForever(mockCryptoListObserver)
        givenListOfCrypto { data ->
            //When
            tested.load()
            //Then
            verify(mockCryptoListObserver).onChanged(Resource.loading())
            verify(mockCryptoListObserver).onChanged(Resource.success(data))

            givenListOfCrypto { data2 ->
                tested.load(refresh = true)
                verify(mockCryptoListObserver).onChanged(Resource.loading(data))
                verify(mockCryptoListObserver).onChanged(Resource.success(data2))
            }
        }
    }

    @Test
    fun `should get error when loading crypto list`() {
        //Given
        tested.topCryptoList.observeForever(mockCryptoListObserver)
        givenErrorListOfCrypto { error ->
            //When
            tested.load()
            //Then
            verify(mockCryptoListObserver).onChanged(Resource.loading())
            verify(mockCryptoListObserver).onChanged(Resource.error(error))
        }
    }

    @Test
    fun `should refresh after error`() {
        //Given
        tested.topCryptoList.observeForever(mockCryptoListObserver)
        givenErrorListOfCrypto { error ->
            //When
            tested.load()
            //Then
            verify(mockCryptoListObserver).onChanged(Resource.loading())
            verify(mockCryptoListObserver).onChanged(Resource.error(error))
            givenListOfCrypto { data ->
                tested.load()
                //Then
                verify(mockCryptoListObserver, times(2)).onChanged(Resource.loading())
                verify(mockCryptoListObserver).onChanged(Resource.success(data))
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods
    ///////////////////////////////////////////////////////////////////////////

    private fun givenListOfCrypto(next: (List<Crypto>) -> Unit) {
        val data = TestDataFactory.makeCryptoList(10)
        val uiData = uiMapper.convert(data, "USD")
        whenever(useCase(any())).thenReturn(flowOf(data))
        next(uiData)
    }

    private fun givenErrorListOfCrypto(next: (Throwable) -> Unit) {
        val error = IOException("")
        val dataFlow = flow<List<DomainCrypto>> { throw error }
        whenever(useCase(any())).thenReturn(dataFlow)
        next(error)
    }
}
