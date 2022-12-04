package dev.ohoussein.cryptoapp.common.mvi

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ReducerTest : DescribeSpec({

    val reducer by lazy { DummyReducer() }

    describe("reduce") {
        val newState = reducer.reduce(DummyUiState(), DummyUiEvent)

        it("should set the state data according to the event") {
            newState shouldBe DummyUiState("from Initial to a new data")
        }
    }
})
