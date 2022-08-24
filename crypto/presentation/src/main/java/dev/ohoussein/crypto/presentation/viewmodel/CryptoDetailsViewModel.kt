package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.navigation.NavPath
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsEvents
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsIntent
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsReducer
import dev.ohoussein.crypto.presentation.reducer.CryptoDetailsState
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.common.mvi.BaseViewModel
import dev.ohoussein.cryptoapp.common.resource.asCachedResourceFlow
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: GetCryptoDetails,
    private val modelMapper: DomainModelMapper,
    private val errorMessageFormatter: ErrorMessageFormatter,
) : BaseViewModel<CryptoDetailsState, CryptoDetailsEvents, CryptoDetailsIntent>() {

    override val reducer = CryptoDetailsReducer()

    private var cryptoId: String = checkNotNull(savedStateHandle[NavPath.CryptoDetailsPath.ARG_CRYPTO_ID])

    override fun handleIntent(intent: CryptoDetailsIntent) {
        when (intent) {
            is CryptoDetailsIntent.ScreenOpened -> load()
            CryptoDetailsIntent.Refresh -> refresh()
        }
    }

    private fun load() {
        useCase(cryptoId, CachePolicy.CACHE_THEN_FRESH)
            .processCryptoDetails()
    }

    private fun refresh() {
        useCase(cryptoId, CachePolicy.FRESH)
            .processCryptoDetails()
    }

    private fun Flow<CachedData<DomainCryptoDetails>>.processCryptoDetails() {
        this.map { it.map(modelMapper.convert(it.data)) }
            .asCachedResourceFlow(errorMessageFormatter::invoke)
            .onEach {
                reducer.sendEvent(CryptoDetailsEvents.UpdateCryptoDetailsResource(it))
            }
            .launchIn(viewModelScope)
    }
}
