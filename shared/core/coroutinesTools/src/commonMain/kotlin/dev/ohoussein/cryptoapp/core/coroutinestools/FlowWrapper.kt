package dev.ohoussein.cryptoapp.core.coroutinestools

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FlowWrapper<T>(private val origin: Flow<T>) : Flow<T> by origin {

    fun subscribe(
        onEach: (T) -> Unit,
    ): Closeable {
        val job = Job()

        onEach { onEach(it) }
            .launchIn(CoroutineScope(Dispatchers.Main + job))

        return Closeable { job.cancel() }
    }
}

fun <T> Flow<T>.wrap(): FlowWrapper<T> = FlowWrapper(this)
