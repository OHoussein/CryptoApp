package dev.ohoussein.cryptoapp.core

import dev.ohoussein.cryptoapp.common.coroutine.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers

class TestCoroutineContextProvider : CoroutineContextProvider() {

    override val main = Dispatchers.Unconfined
    override val io = Dispatchers.Unconfined
}