package dev.ohoussein.cryptoapp.common.mvi

internal data class DummyUiState(val data: String = "Initial") : UiState
internal object DummyUiEvent : UiEvent
internal object DummyUiIntent : UiIntent

internal class DummyReducer : Reducer<DummyUiState, DummyUiEvent>(DummyUiState()) {

    override fun reduce(oldState: DummyUiState, event: DummyUiEvent): DummyUiState {
        return oldState.copy(data = "from ${oldState.data} to a new data")
    }
}
