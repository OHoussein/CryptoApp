package dev.ohoussein.cryptoapp.common.mvi

import dev.ohoussein.core.test.extension.TestCoroutineExtension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class BaseViewModelTest : DescribeSpec({

    extension(TestCoroutineExtension())

    val viewModel by lazy { DummyViewModel() }

    describe("dispatch") {
        beforeEach {
            viewModel.dispatch(DummyUiIntent)
        }

        it("should handle update the state according to the intent") {
            viewModel.state.value shouldBe DummyUiState("from Initial to a new data")
            viewModel.stateValue shouldBe DummyUiState("from Initial to a new data")
        }
    }
})

private class DummyViewModel : BaseViewModel<DummyUiState, DummyUiEvent, DummyUiIntent>() {

    override val reducer = DummyReducer()

    override fun handleIntent(intent: DummyUiIntent) {
        reducer.sendEvent(DummyUiEvent)
    }
}
