package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.NavPath
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsEvents
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsReducer
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsState
import dev.ohoussein.cryptoapp.common.mvi.BaseViewModel
import dev.ohoussein.cryptoapp.common.resource.asDataStatusFlow
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class CryptoDetailsViewModel constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetCryptoDetails,
    private val modelMapper: DomainModelMapper,
    private val errorMessageFormatter: ErrorMessageFormatter,
) : BaseViewModel<CryptoDetailsState, CryptoDetailsEvents, CryptoDetailsIntent>() {

    override val reducer = CryptoDetailsReducer()

    private val cryptoId: String by lazy { checkNotNull(savedStateHandle[NavPath.CryptoDetailsPath.ARG_CRYPTO_ID]) }

    override fun handleIntent(intent: CryptoDetailsIntent) {
        when (intent) {
            is CryptoDetailsIntent.ScreenOpened -> onScreenOpened()
            CryptoDetailsIntent.Refresh -> refresh()
        }
    }

    private fun onScreenOpened() {
        useCase(cryptoId)
            .map(modelMapper::convert)
            .onEach {
                reducer.sendEvent(CryptoDetailsEvents.UpdateCryptoDetails(it))
            }
            .launchIn(viewModelScope)
        refresh()
    }

    private fun refresh() {
        asDataStatusFlow(errorMessageFormatter::invoke) { useCase.refresh(cryptoId) }
            .onEach {
                reducer.sendEvent(CryptoDetailsEvents.UpdateStatus(it))
            }
            .launchIn(viewModelScope)
    }
}
