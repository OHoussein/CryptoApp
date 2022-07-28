package dev.ohoussein.crypto.presentation.cryptolist

import androidx.lifecycle.Observer
import dev.ohoussein.core.test.extension.InstantTaskExecutorExtension
import dev.ohoussein.core.test.extension.TestCoroutineExtension
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.common.resource.Resource
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito.times
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

class CryptoDetailsModelTest : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerTest

    extension(TestCoroutineExtension())
    extension(InstantTaskExecutorExtension())

    val cryptoId = "bitcoin"

    val useCase = mock<GetCryptoDetails>()
    val uiMapper = mock<DomainModelMapper>()
    val stateObserver = mock<Observer<Resource<CryptoDetails>>>()
    val viewModel by lazy {
        CryptoDetailsViewModel(
            useCase = useCase,
            modelMapper = uiMapper,
        ).apply {
            cryptoDetails.observeForever(stateObserver)
        }
    }

    given("crypto details") {
        val domainCryptoDetails: DomainCryptoDetails = mock()
        val cryptoDetails: CryptoDetails = mock()
        whenever(uiMapper.convert(domainCryptoDetails)).thenReturn(cryptoDetails)
        whenever(useCase(any())).thenReturn(flowOf(domainCryptoDetails))

        `when`("load crypto details") {
            viewModel.load(cryptoId)

            then("the state should takes loading then success") {
                verify(stateObserver).onChanged(Resource.loading())
                verify(stateObserver).onChanged(Resource.success(cryptoDetails))
            }
        }
    }

    given("an error on getting crypto details") {
        val error = IOException("")
        whenever(useCase(any())).thenReturn(flow { throw error })

        `when`("load crypto details") {
            viewModel.load(cryptoId)

            then("the state should takes loading then error") {
                verify(stateObserver).onChanged(Resource.loading())
                verify(stateObserver).onChanged(Resource.error(error))
            }
        }
    }

    given("an error then success on getting crypto details") {
        val domainCryptoDetails: DomainCryptoDetails = mock()
        val cryptoDetails: CryptoDetails = mock()
        whenever(uiMapper.convert(domainCryptoDetails)).thenReturn(cryptoDetails)

        val error = IOException("")

        whenever(useCase(any())).thenReturn(flow { throw error }).thenReturn(flowOf(domainCryptoDetails))

        `when`("load crypto details 2 times") {
            viewModel.load(cryptoId)
            viewModel.load(cryptoId)

            then("the state should takes loading then error") {
                verify(stateObserver, times(2)).onChanged(Resource.loading())
                verify(stateObserver).onChanged(Resource.error(error))
                verify(stateObserver).onChanged(Resource.success(cryptoDetails))
            }
        }
    }
})
