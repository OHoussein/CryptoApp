package dev.ohoussein.cryptoapp.common.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

abstract class Reducer<S : UiState, E : UiEvent>(
    initialState: S,
) {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state

    fun sendEvent(event: E) {
        setState(reduce(_state.value, event))
    }

    private fun setState(newState: S) {
        Timber.d("setState: $newState")
        println("setState: $newState")
        _state.tryEmit(newState)
    }

    abstract fun reduce(oldState: S, event: E): S
}

interface UiState

interface UiEvent

interface UiIntent
