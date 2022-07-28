package dev.ohoussein.cryptoapp.common.resource

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ResourceTest : DescribeSpec({

    val data = "Hello"

    describe("a success Resource") {
        val resource = Resource.success(data)

        it("should be a success resource") {
            resource.status shouldBe Status.SUCCESS
            resource.error shouldBe null
            resource.data shouldBe data
        }
    }

    describe("a loading Resource") {

        describe("without data") {
            val resource = Resource.loading<String>()

            it("should be a loading resource") {
                resource.status shouldBe Status.LOADING
                resource.error shouldBe null
                resource.data shouldBe null
            }
        }

        describe("with data") {
            val resource = Resource.loading(data)

            it("should be a loading resource with data") {
                resource.status shouldBe Status.LOADING
                resource.error shouldBe null
                resource.data shouldBe data
            }
        }
    }

    describe("an error Resource") {
        val error = Exception()

        describe("without data") {
            val resource = Resource.error<String>(error)

            it("should be an error resource") {
                resource.status shouldBe Status.ERROR
                resource.error shouldBe error
                resource.data shouldBe null
            }
        }

        describe("with data") {
            val resource = Resource.error(error, data)

            it("should be an error resource with data") {
                resource.status shouldBe Status.ERROR
                resource.error shouldBe error
                resource.data shouldBe data
            }
        }
    }
})
