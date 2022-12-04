package dev.ohoussein.core.test.extension

import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineExtension(
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : BeforeSpecListener, AfterSpecListener {

    override suspend fun beforeSpec(spec: Spec) {
        Dispatchers.setMain(dispatcher)
    }

    override suspend fun afterSpec(spec: Spec) {
        Dispatchers.resetMain()
    }
}
