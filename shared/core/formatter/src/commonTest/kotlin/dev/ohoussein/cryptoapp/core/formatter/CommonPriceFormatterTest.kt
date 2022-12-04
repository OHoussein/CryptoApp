package dev.ohoussein.cryptoapp.core.formatter

import kotlin.test.Test
import kotlin.test.assertEquals

private const val CURRENCY = "USD"

class CommonPriceFormatterTest {

    private val priceFormatter = getPriceFormatter("en")

    @Test
    fun should_format_a_price_without_fraction() {
        assertEquals("$120", priceFormatter(120.0, CURRENCY))
    }

    @Test
    fun should_format_a_price_with_fraction() {
        assertEquals("$120.51", priceFormatter(120.51, CURRENCY))
    }
}
