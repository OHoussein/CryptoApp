package dev.ohoussein.crypto.presentation.cryptolist

import androidx.lifecycle.Observer
import dev.ohoussein.core.test.extension.InstantTaskExecutorExtension
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.viewmodel.HomeViewModel
import dev.ohoussein.cryptoapp.common.resource.Resource
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.mockito.Mockito.times
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class HomeViewModelTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerTest

    extension(TestCoroutineExtension())
    extension(InstantTaskExecutorExtension())

    val useCase = mock<GetTopCryptoList>()
    val uiMapper = mock<DomainModelMapper>()
    val stateObserver = mock<Observer<Resource<Unit>>>()

    val homeViewModel by lazy {
        HomeViewModel(
            useCase = useCase,
            modelMapper = uiMapper,
        ).apply {
            syncState.observeForever(stateObserver)
        }
    }

    given("a list of crypto") {
        val data: List<DomainCrypto> = mock()
        val uiData: List<Crypto> = mock()
        whenever(uiMapper.convert(data, "USD")).thenReturn(uiData)
        whenever(useCase.refresh(any())).thenReturn(Unit)

        `when`("refresh") {
            homeViewModel.refresh()

            then("it should get 1 loading state and 1 success state") {
                verify(stateObserver).onChanged(Resource.loading())
                verify(stateObserver).onChanged(Resource.success(Unit))
            }
        }

        `when`("refresh then force refresh") {
            homeViewModel.refresh()
            homeViewModel.refresh(force = true)

            then("it should get 2 loading state and 2 success state") {
                verify(stateObserver, times(2)).onChanged(Resource.loading())
                verify(stateObserver, times(2)).onChanged(Resource.success(Unit))
            }
        }
    }

    given("an error on getting the list of crypto") {
        val error = IOException("")
        whenever(useCase.refresh(any())).thenAnswer { throw error }

        `when`("refresh") {
            homeViewModel.refresh()

            then("it should get 1 loading state and 1 error state") {
                verify(stateObserver).onChanged(Resource.loading())
                verify(stateObserver).onChanged(Resource.error(error))
            }
        }
    }

    given("an error then success on getting the list of crypto") {
        val error = IOException("")

        val data: List<DomainCrypto> = mock()
        val uiData: List<Crypto> = mock()
        whenever(uiMapper.convert(data, "USD")).thenReturn(uiData)

        whenever(useCase.refresh(any())).thenAnswer { throw error }.thenReturn(Unit)

        `when`("refresh two times") {
            homeViewModel.refresh()
            homeViewModel.refresh()

            then("it should get 1 loading state and 1 error state") {
                verify(stateObserver, times(2)).onChanged(Resource.loading())
                verify(stateObserver).onChanged(Resource.error(error))
                verify(stateObserver).onChanged(Resource.success(Unit))
            }
        }
    }
})
