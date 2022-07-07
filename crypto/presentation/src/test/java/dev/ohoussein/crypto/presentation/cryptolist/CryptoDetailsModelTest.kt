package dev.ohoussein.crypto.presentation.cryptolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dev.ohoussein.core.test.coroutine.TestCoroutineRule
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.presentation.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.times
import java.io.IOException

@ExperimentalCoroutinesApi
class CryptoDetailsModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var tested: CryptoDetailsViewModel
    private lateinit var useCase: GetCryptoDetails

    private lateinit var uiMapper: DomainModelMapper
    private val mockObserver = mock<Observer<Resource<CryptoDetails>>>()

    private val cryptoId = "bitcoin"

    @Before
    fun setup() {
        useCase = mock()
        uiMapper = mock()
        tested = CryptoDetailsViewModel(
            useCase,
            uiMapper,
        )
        tested.cryptoDetails.observeForever(mockObserver)
    }

    @Test
    fun `should load top crypto list`() {
        // Given
        givenCrypto { data ->
            // When
            tested.load(cryptoId)
            // Then
            verify(mockObserver).onChanged(Resource.loading())
            verify(mockObserver).onChanged(Resource.success(data))
        }
    }

    @Test
    fun `should get error when loading crypto list`() {
        // Given
        givenError { error ->
            // When
            tested.load(cryptoId)
            // Then
            verify(mockObserver).onChanged(Resource.loading())
            verify(mockObserver).onChanged(Resource.error(error))
        }
    }

    @Test
    fun `should refresh after error`() {
        // Given
        givenError { error ->
            // When
            tested.load(cryptoId)
            // Then
            verify(mockObserver).onChanged(Resource.loading())
            verify(mockObserver).onChanged(Resource.error(error))
            givenCrypto { data ->
                tested.load(cryptoId)
                // Then
                verify(mockObserver, times(2)).onChanged(Resource.loading())
                verify(mockObserver).onChanged(Resource.success(data))
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // private methods
    // /////////////////////////////////////////////////////////////////////////

    private fun givenCrypto(next: (CryptoDetails) -> Unit) {
        val data: DomainCryptoDetails = mock()
        val uiData: CryptoDetails = mock()
        whenever(uiMapper.convert(data)).thenReturn(uiData)
        whenever(useCase(any())).thenReturn(flowOf(data))
        next(uiData)
    }

    private fun givenError(next: (Throwable) -> Unit) {
        val error = IOException("")
        val dataFlow = flow<DomainCryptoDetails> { throw error }
        whenever(useCase(any())).thenReturn(dataFlow)
        next(error)
    }
}
