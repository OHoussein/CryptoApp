package dev.ohoussein.crypto.presentation.reducer

import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.common.mvi.Reducer
import dev.ohoussein.cryptoapp.common.mvi.UiEvent
import dev.ohoussein.cryptoapp.common.mvi.UiIntent
import dev.ohoussein.cryptoapp.common.mvi.UiState
import dev.ohoussein.cryptoapp.common.resource.Resource

class CryptoDetailsReducer : Reducer<CryptoDetailsState, CryptoDetailsEvents>(CryptoDetailsState()) {

    override fun reduce(oldState: CryptoDetailsState, event: CryptoDetailsEvents): CryptoDetailsState {
        return when (event) {
            is CryptoDetailsEvents.UpdateCryptoDetailsResource -> {
                oldState.copy(crypto = event.crypto)
            }
        }
    }
}

sealed class CryptoDetailsEvents : UiEvent {
    data class UpdateCryptoDetailsResource(val crypto: Resource<CryptoDetails>) : CryptoDetailsEvents()
}

sealed class CryptoDetailsIntent : UiIntent {
    object ScreenOpened : CryptoDetailsIntent()
    object Refresh : CryptoDetailsIntent()
}

data class CryptoDetailsState(
    val crypto: Resource<CryptoDetails> = Resource.loading(),
) : UiState
