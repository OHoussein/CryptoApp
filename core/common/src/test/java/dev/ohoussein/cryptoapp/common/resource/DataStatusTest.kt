package dev.ohoussein.cryptoapp.common.resource

import app.cash.turbine.test
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class DataStatusTest : DescribeSpec({

    coroutineTestScope = true
    isolationMode = IsolationMode.InstancePerTest

    val errorMessageMapper: (Throwable) -> String = { "error" }

    describe("Success block") {
        val flow = asDataStatusFlow(errorMessageMapper) {}

        it("should receive loading status then Success") {
            flow.test {
                awaitItem() shouldBe DataStatus.Loading
                awaitItem() shouldBe DataStatus.Success
                awaitComplete()
            }
        }
    }

    describe("Error block") {
        val flow = asDataStatusFlow(errorMessageMapper) { throw Error() }

        it("should receive loading status then Error") {
            flow.test {
                awaitItem() shouldBe DataStatus.Loading
                awaitItem() shouldBe DataStatus.Error("error")
                awaitComplete()
            }
        }
    }
})
