package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.reducer.CryptoListEvents
import dev.ohoussein.crypto.presentation.reducer.CryptoListIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoListReducer
import dev.ohoussein.crypto.presentation.reducer.CryptoListState
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.common.mvi.BaseViewModel
import dev.ohoussein.cryptoapp.common.resource.asCachedResourceFlow
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val useCase: GetTopCryptoList,
    private val modelMapper: DomainModelMapper,
    private val errorMessageFormatter: ErrorMessageFormatter,
) : BaseViewModel<CryptoListState, CryptoListEvents, CryptoListIntent>() {

    override val reducer = CryptoListReducer()

    override fun handleIntent(intent: CryptoListIntent) = when (intent) {
        CryptoListIntent.ScreenOpened -> onScreenOpened()
        CryptoListIntent.Refresh -> onRefresh()
    }

    private fun onScreenOpened() {
        if (stateValue.cryptoList.isSuccess)
            return
        useCase.get(CachePolicy.CACHE_THEN_FRESH)
            .processCryptoList()
    }

    private fun onRefresh() {
        useCase.get(CachePolicy.FRESH)
            .processCryptoList()
    }

    private fun Flow<CachedData<List<DomainCrypto>>>.processCryptoList() {
        this.map { it.map(modelMapper.convert(it.data)) }
            .asCachedResourceFlow(errorMessageFormatter::invoke, stateValue.cryptoList.data)
            .onEach {
                reducer.sendEvent(CryptoListEvents.UpdateCryptoListResource(it))
            }
            .launchIn(viewModelScope)
    }
}
