package dev.ohoussein.cryptoapp.core.formatter

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PercentFormatterTest : DescribeSpec({

    describe("a PercentFormatter") {

        val percentFormatter = PercentFormatter()

        it("should format a percent without fraction") {
            percentFormatter(0.41) shouldBe "41.00%"
        }

        it("should format a percent with 1 fractions") {
            percentFormatter(0.123) shouldBe "12.30%"
        }

        it("should format a percent with 2 fractions") {
            percentFormatter(0.1234) shouldBe "12.34%"
        }
    }
})
