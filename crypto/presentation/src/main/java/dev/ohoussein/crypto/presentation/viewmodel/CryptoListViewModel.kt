package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.reducer.CryptoListEvents
import dev.ohoussein.crypto.presentation.reducer.CryptoListIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoListReducer
import dev.ohoussein.crypto.presentation.reducer.CryptoListState
import dev.ohoussein.cryptoapp.common.mvi.BaseViewModel
import dev.ohoussein.cryptoapp.common.resource.asDataStatusFlow
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class CryptoListViewModel constructor(
    private val useCase: GetTopCryptoList,
    private val modelMapper: DomainModelMapper,
    private val errorMessageFormatter: ErrorMessageFormatter,
) : BaseViewModel<CryptoListState, CryptoListEvents, CryptoListIntent>() {

    override val reducer = CryptoListReducer()

    override fun handleIntent(intent: CryptoListIntent) = when (intent) {
        CryptoListIntent.ScreenOpened -> onScreenOpened()
        CryptoListIntent.Refresh -> refresh()
    }

    private fun onScreenOpened() {
        val dataExist = stateValue.cryptoList != null
        useCase()
            .map(modelMapper::convert)
            .onEach {
                reducer.sendEvent(CryptoListEvents.UpdateCryptoList(it))
            }
            .launchIn(viewModelScope)
        if (!dataExist) {
            refresh()
        }
    }

    private fun refresh() {
        asDataStatusFlow(errorMessageFormatter::invoke, useCase::refresh)
            .onEach {
                reducer.sendEvent(CryptoListEvents.UpdateStatus(it))
            }
            .launchIn(viewModelScope)
    }
}
