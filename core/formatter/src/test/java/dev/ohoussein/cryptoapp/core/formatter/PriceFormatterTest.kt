package dev.ohoussein.cryptoapp.core.formatter

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

private const val CURRENCY = "USD"

class PriceFormatterTest : DescribeSpec({

    describe("a PriceFormatter") {

        val priceFormatter = PriceFormatter()

        it("should format a price without fraction") {
            priceFormatter(120.0, CURRENCY) shouldBe "$120.00"
        }

        it("should format a price with fraction") {
            priceFormatter(120.51, CURRENCY) shouldBe "$120.51"
        }
    }
})
