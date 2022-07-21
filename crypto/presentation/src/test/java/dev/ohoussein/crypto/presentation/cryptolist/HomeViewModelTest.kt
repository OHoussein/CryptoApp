package dev.ohoussein.crypto.presentation.cryptolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import dev.ohoussein.core.test.coroutine.TestCoroutineRule
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.viewmodel.HomeViewModel
import dev.ohoussein.cryptoapp.common.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.times
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var tested: HomeViewModel
    private lateinit var useCase: GetTopCryptoList

    private lateinit var uiMapper: DomainModelMapper
    private lateinit var stateObserver: Observer<Resource<Unit>>

    @Before
    fun setup() {
        useCase = mock()
        uiMapper = mock()
        tested = HomeViewModel(
            useCase,
            uiMapper,
        )
        stateObserver = mock()
        tested.syncState.observeForever(stateObserver)
    }

    @Test
    fun `should load top crypto list`() = runTest {
        // Given
        givenListOfCrypto {
            // When
            tested.refresh(true)
            // Then
            verify(stateObserver, atLeast(1)).onChanged(Resource.loading())
            verify(stateObserver, atLeast(1)).onChanged(Resource.success(Unit))
        }
    }

    @Test
    fun `should get latest data when refreshing`() = runTest {
        // Given
        givenListOfCrypto {
            // When
            tested.refresh()
            // Then
            verify(stateObserver).onChanged(Resource.success(Unit))
            tested.refresh(force = true)
            verify(stateObserver, atLeast(1)).onChanged(Resource.loading())
            verify(stateObserver, times(2)).onChanged(Resource.success(Unit))
        }
    }

    @Test
    fun `should get error when loading crypto list`() = runTest {
        // Given
        val error = givenErrorListOfCrypto()
        // When
        tested.refresh(true)
        // Then
        verify(stateObserver).onChanged(Resource.loading())
        verify(stateObserver).onChanged(Resource.error(error))
    }

    @Test
    fun `should refresh after error`() = runTest {
        // Given
        val error = givenErrorListOfCrypto()
        // When
        tested.refresh(true)
        // Then
        verify(stateObserver).onChanged(Resource.loading())
        verify(stateObserver).onChanged(Resource.error(error))
        givenListOfCrypto {
            tested.refresh(true)
            // Then
            verify(stateObserver, times(2)).onChanged(Resource.loading())
            verify(stateObserver, atLeast(1)).onChanged(Resource.success(Unit))
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // private methods
    // /////////////////////////////////////////////////////////////////////////

    private suspend fun givenListOfCrypto(next: (List<Crypto>) -> Unit) {
        val data: List<DomainCrypto> = mock()
        val uiData: List<Crypto> = mock()
        whenever(uiMapper.convert(data, "USD")).thenReturn(uiData)
        whenever(useCase.refresh(any())).thenReturn(Unit)
        next(uiData)
    }

    private suspend fun givenErrorListOfCrypto(): Throwable {
        val error = IOException("")
        whenever(useCase.refresh(any())).thenAnswer { throw error }
        return error
    }
}
