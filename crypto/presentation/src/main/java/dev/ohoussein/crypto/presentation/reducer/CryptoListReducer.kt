package dev.ohoussein.crypto.presentation.reducer

import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.common.mvi.Reducer
import dev.ohoussein.cryptoapp.common.mvi.UiEvent
import dev.ohoussein.cryptoapp.common.mvi.UiIntent
import dev.ohoussein.cryptoapp.common.mvi.UiState
import dev.ohoussein.cryptoapp.common.resource.Resource

class CryptoListReducer : Reducer<CryptoListState, CryptoListEvents>(CryptoListState()) {

    override fun reduce(oldState: CryptoListState, event: CryptoListEvents): CryptoListState {
        return when (event) {
            is CryptoListEvents.UpdateCryptoListResource -> {
                oldState.copy(cryptoList = event.cryptoList)
            }
        }
    }
}

sealed class CryptoListEvents : UiEvent {
    data class UpdateCryptoListResource(val cryptoList: Resource<List<Crypto>>) : CryptoListEvents()
}

sealed class CryptoListIntent : UiIntent {
    object ScreenOpened : CryptoListIntent()
    object Refresh : CryptoListIntent()
    // data class ShowCryptoDetails(val cryptoId: String): CryptoListIntent()
}

sealed class CryptoListViewEffect {
    data class NavigateToCryptoDetails(val cryptoId: String)
}

data class CryptoListState(
    val cryptoList: Resource<List<Crypto>> = Resource.loading(),
) : UiState
