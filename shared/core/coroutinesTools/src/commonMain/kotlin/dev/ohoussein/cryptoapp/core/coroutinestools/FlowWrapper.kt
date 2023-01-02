package dev.ohoussein.cryptoapp.core.coroutinestools

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

class FlowWrapper<T>(private val origin: Flow<T>) : Flow<T> by origin {

    fun subscribe(
        onEach: (T) -> Unit,
        onError: (error: Throwable) -> Unit,
        onCompletion: () -> Unit,
    ): Closeable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        onEach { onEach(it) }
            .catch {
                if (scope.isActive || it !is CancellationException)
                    onError(it)
            }
            .onCompletion {
                onCompletion()
            }
            .launchIn(scope)

        return Closeable { scope.cancel() }
    }
}

inline fun <T> Flow<T>.wrap(): FlowWrapper<T> = FlowWrapper(this)
