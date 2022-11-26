package dev.ohoussein.crypto.presentation.reducer

import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.common.mvi.Reducer
import dev.ohoussein.cryptoapp.common.mvi.UiEvent
import dev.ohoussein.cryptoapp.common.mvi.UiIntent
import dev.ohoussein.cryptoapp.common.mvi.UiState
import dev.ohoussein.cryptoapp.common.resource.DataStatus

class CryptoListReducer : Reducer<CryptoListState, CryptoListEvents>(CryptoListState()) {

    override fun reduce(oldState: CryptoListState, event: CryptoListEvents): CryptoListState {
        return when (event) {
            is CryptoListEvents.UpdateCryptoList -> {
                oldState.copy(cryptoList = event.cryptoList, status = DataStatus.Success)
            }
            is CryptoListEvents.UpdateStatus -> {
                oldState.copy(status = event.status)
            }
        }
    }
}

sealed class CryptoListEvents : UiEvent {
    data class UpdateCryptoList(val cryptoList: List<Crypto>) : CryptoListEvents()
    data class UpdateStatus(val status: DataStatus) : CryptoListEvents()
}

sealed class CryptoListIntent : UiIntent {
    object ScreenOpened : CryptoListIntent()
    object Refresh : CryptoListIntent()
}

data class CryptoListState(
    val cryptoList: List<Crypto>? = null,
    val status: DataStatus = DataStatus.Initial,
) : UiState
