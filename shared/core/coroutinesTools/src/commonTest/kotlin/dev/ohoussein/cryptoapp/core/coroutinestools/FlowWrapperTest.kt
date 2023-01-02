package dev.ohoussein.cryptoapp.core.coroutinestools

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class FlowWrapperTest {

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_get_data_from_subscribe() {
        // Given
        val expectedData = "data content"
        val flow = flowOf(expectedData)
        var receivedFromOnEach: String? = null
        var onCompletionCalled = false
        var receivedFromOnError: Throwable? = null

        // When
        flow.wrap()
            .subscribe(
                onEach = { receivedFromOnEach = it },
                onCompletion = { onCompletionCalled = true },
                onError = { receivedFromOnError = it }
            )

        // Then
        assertEquals(expectedData, receivedFromOnEach)
        assertTrue(onCompletionCalled)
        assertNull(receivedFromOnError)
    }

    @Test
    fun should_cancel_subscription() {
        runTest {
            // Given
            val flow = flow {
                delay(1000)
                emit("any data")
            }
            var receivedFromOnEach: String? = null
            var onCompletionCalled = false
            var receivedFromOnError: Throwable? = null

            // When
            val closeable = flow.wrap()
                .subscribe(
                    onEach = { receivedFromOnEach = it },
                    onCompletion = { onCompletionCalled = true },
                    onError = { receivedFromOnError = it }
                )
            advanceTimeBy(200)
            closeable.close()
            advanceUntilIdle()

            // Then
            assertNull(receivedFromOnEach)
            assertNull(receivedFromOnError)
            assertTrue(onCompletionCalled)
        }
    }

    @Test
    fun should_get_error_in_the_onCompletion() {
        // Given
        val exception = Exception("Fake error")
        val flow = flow<String> { throw exception }
        var receivedFromOnEach: String? = null
        var onCompletionCalled = false
        var receivedFromOnError: Throwable? = null

        // When
        flow.wrap()
            .subscribe(
                onEach = { receivedFromOnEach = it },
                onCompletion = { onCompletionCalled = true },
                onError = { receivedFromOnError = it }
            )

        // Then
        assertNull(receivedFromOnEach)
        assertTrue(onCompletionCalled)
        assertEquals(exception, receivedFromOnError)
    }
}
