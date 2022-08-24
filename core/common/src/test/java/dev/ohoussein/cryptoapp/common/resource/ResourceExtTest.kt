package dev.ohoussein.cryptoapp.common.resource

import app.cash.turbine.test
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.io.IOException

class ResourceExtTest : DescribeSpec({

    coroutineTestScope = true
    isolationMode = IsolationMode.InstancePerTest

    val data = "Hello"

    val errorMessageMapper: (Throwable) -> String = { "error" }

    describe("a Flow") {

        context("a success flow") {
            val flow = flowOf(data)

            describe("asResourceFlow without previous data") {
                val resourceFlow = flow.asResourceFlow(errorMessageMapper)

                it("should receive loading state") {
                    resourceFlow.test {
                        val firstItem = awaitItem()
                        firstItem.status shouldBe Status.LOADING
                        firstItem.data shouldBe null

                        val secondItem = awaitItem()
                        secondItem.status shouldBe Status.SUCCESS
                        secondItem.data shouldBe data

                        awaitComplete()
                    }
                }
            }

            describe("asResourceFlow with previous data") {
                val previousData = "Hi"
                val resourceFlow = flow.asResourceFlow(errorMessageMapper, previousData)

                it("should receive loading state with previousData") {
                    resourceFlow.test {
                        val firstItem = awaitItem()
                        firstItem.status shouldBe Status.LOADING
                        firstItem.data shouldBe previousData

                        val secondItem = awaitItem()
                        secondItem.status shouldBe Status.SUCCESS
                        secondItem.data shouldBe data

                        awaitComplete()
                    }
                }
            }
        }

        context("an error flow") {
            val flow = flow<String> { throw IOException() }

            describe("asResourceFlow without previous data") {
                val resourceFlow = flow.asResourceFlow(errorMessageMapper)

                resourceFlow.collect()

                it("should receive loading state") {
                    resourceFlow.test {
                        val firstItem = awaitItem()
                        firstItem.status shouldBe Status.LOADING
                        firstItem.data shouldBe null

                        val secondItem = awaitItem()
                        secondItem.status shouldBe Status.ERROR
                        secondItem.data shouldBe null
                        secondItem.error shouldBe "error"

                        awaitComplete()
                    }
                }
            }

            describe("asResourceFlow with previous data") {
                val previousData = "Hi"
                val resourceFlow = flow.asResourceFlow(errorMessageMapper, previousData)

                resourceFlow.collect()

                it("should receive loading state with previousData") {
                    resourceFlow.test {
                        val firstItem = awaitItem()
                        firstItem.status shouldBe Status.LOADING
                        firstItem.data shouldBe previousData

                        val secondItem = awaitItem()
                        secondItem.status shouldBe Status.ERROR
                        secondItem.data shouldBe previousData
                        secondItem.error shouldBe "error"

                        awaitComplete()
                    }
                }
            }
        }
    }
})
