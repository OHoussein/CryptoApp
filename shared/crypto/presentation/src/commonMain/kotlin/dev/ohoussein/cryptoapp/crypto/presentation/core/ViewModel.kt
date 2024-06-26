package dev.ohoussein.cryptoapp.crypto.presentation.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel as StateHolderViewModel

abstract class ViewModel<State : Any, in Event>(initialState: State) : StateHolderViewModel() {
    val state: StateFlow<State>
        get() = mutableState

    protected val mutableState: MutableStateFlow<State> = MutableStateFlow(initialState)

    protected val viewModelScope = CoroutineScope(Dispatchers.Main)

    abstract fun dispatch(event: Event)
}
