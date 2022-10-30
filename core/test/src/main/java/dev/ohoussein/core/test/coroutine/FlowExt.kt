package dev.ohoussein.core.test.coroutine

import java.io.IOException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <T> flowOfWithDelays(vararg elements: T, delayMs: Long = 300): Flow<T> = flow {
    elements.forEach {
        delay(delayMs)
        emit(it)
    }
}

fun <T> flowOfError(error: Throwable = IOException(), delayMs: Long = 0): Flow<T> = flow {
    if (delayMs > 0)
        delay(delayMs)
    throw error
}
