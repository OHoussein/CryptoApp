package dev.ohoussein.cryptoapp.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

abstract class BaseViewModel<State : UiState, Event : UiEvent, in Intent : UiIntent> : ViewModel() {

    abstract val reducer: Reducer<State, Event>

    val state: StateFlow<State>
        get() = reducer.state
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), stateValue)

    val stateValue: State
        get() = reducer.state.value

    private val intentFlow = MutableSharedFlow<Intent>()

    init {
        intentFlow
            .onEach {
                Timber.d("Intent: $it")
                handleIntent(it)
            }
            .launchIn(viewModelScope)
    }

    fun dispatch(intent: Intent) {
        viewModelScope.launch { intentFlow.emit(intent) }
    }

    protected abstract fun handleIntent(intent: Intent)
}
