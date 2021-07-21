package dev.ohoussein.cryptoapp.ui.cryptolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.cryptoapp.commonTest.mock.TestDataFactory
import dev.ohoussein.cryptoapp.commonTest.rule.TestCoroutineRule
import dev.ohoussein.cryptoapp.core.TestCoroutineContextProvider
import dev.ohoussein.cryptoapp.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.ui.core.UiCoreModule
import dev.ohoussein.cryptoapp.ui.core.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.Resource
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.times
import java.io.IOException

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

    private lateinit var stateObserver: Observer<Resource<Unit>>

    @Before
    fun setup() {
        useCase = mock()
        tested = HomeViewModel(
            TestCoroutineContextProvider(),
            useCase,
            uiMapper,
        )
        stateObserver = mock()
        tested.syncState.observeForever(stateObserver)
    }

    @Test
    fun `should load top crypto list`() = runBlockingTest {
        //Given
        givenListOfCrypto {
            //When
            tested.refresh(true)
            //Then
            verify(stateObserver, atLeast(1)).onChanged(Resource.loading())
            verify(stateObserver, atLeast(1)).onChanged(Resource.success(Unit))
        }
    }

    @Test
    fun `should get latest data when refreshing`() = runBlockingTest {
        //Given
        givenListOfCrypto {
            //When
            tested.refresh()
            //Then
            verify(stateObserver).onChanged(Resource.success(Unit))
            runBlockingTest {
                givenListOfCrypto {
                    tested.refresh(force = true)
                    verify(stateObserver, atLeast(1)).onChanged(Resource.loading())
                    verify(stateObserver, times(2)).onChanged(Resource.success(Unit))
                }
            }
        }
    }

    @Test
    fun `should get error when loading crypto list`() = runBlockingTest {
        //Given
        givenErrorListOfCrypto { error ->
            //When
            tested.refresh(true)
            //Then
            verify(stateObserver).onChanged(Resource.loading())
            verify(stateObserver).onChanged(Resource.error(error))
        }
    }

    @Test
    fun `should refresh after error`() = runBlockingTest {
        //Given
        givenErrorListOfCrypto { error ->
            //When
            tested.refresh(true)
            //Then
            verify(stateObserver).onChanged(Resource.loading())
            verify(stateObserver).onChanged(Resource.error(error))
            runBlockingTest {
                givenListOfCrypto {
                    tested.refresh(true)
                    //Then
                    verify(stateObserver, times(2)).onChanged(Resource.loading())
                    verify(stateObserver, atLeast(1)).onChanged(Resource.success(Unit))
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods
    ///////////////////////////////////////////////////////////////////////////

    private suspend fun givenListOfCrypto(next: (List<Crypto>) -> Unit) {
        val data = TestDataFactory.makeCryptoList(10)
        val uiData = uiMapper.convert(data, "USD")
        whenever(useCase.refresh(any())).thenReturn(Unit)
        next(uiData)
    }

    private suspend fun givenErrorListOfCrypto(next: (Throwable) -> Unit) {
        val error = IOException("")
        whenever(useCase.refresh(any())).thenAnswer { throw error }
        next(error)
    }
}
