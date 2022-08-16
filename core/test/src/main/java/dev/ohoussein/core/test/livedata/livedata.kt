package dev.ohoussein.core.test.livedata

import androidx.lifecycle.Observer
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock

inline fun <reified T : Any> mockedObserverOf(): Observer<T> = mock()

fun <T> Observer<T>.verifyStates(vararg states: T) {
    inOrder(this) {
        states.forEach {
            verify(this@verifyStates).onChanged(it)
        }
    }
}
