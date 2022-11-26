package dev.ohoussein.crypto.presentation.reducer

import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.common.mvi.Reducer
import dev.ohoussein.cryptoapp.common.mvi.UiEvent
import dev.ohoussein.cryptoapp.common.mvi.UiIntent
import dev.ohoussein.cryptoapp.common.mvi.UiState
import dev.ohoussein.cryptoapp.common.resource.DataStatus

class CryptoDetailsReducer : Reducer<CryptoDetailsState, CryptoDetailsEvents>(CryptoDetailsState()) {

    override fun reduce(oldState: CryptoDetailsState, event: CryptoDetailsEvents): CryptoDetailsState {
        return when (event) {
            is CryptoDetailsEvents.UpdateCryptoDetails -> {
                oldState.copy(cryptoDetails = event.crypto, status = DataStatus.Success)
            }
            is CryptoDetailsEvents.UpdateStatus -> {
                oldState.copy(status = event.status)
            }
        }
    }
}

sealed class CryptoDetailsEvents : UiEvent {
    data class UpdateCryptoDetails(val crypto: CryptoDetails) : CryptoDetailsEvents()
    data class UpdateStatus(val status: DataStatus) : CryptoDetailsEvents()
}

sealed class CryptoDetailsIntent : UiIntent {
    object ScreenOpened : CryptoDetailsIntent()
    object Refresh : CryptoDetailsIntent()
}

data class CryptoDetailsState(
    val cryptoDetails: CryptoDetails? = null,
    val status: DataStatus = DataStatus.Initial,
) : UiState
