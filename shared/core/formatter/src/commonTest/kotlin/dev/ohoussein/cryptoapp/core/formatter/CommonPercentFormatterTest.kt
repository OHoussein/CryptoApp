package dev.ohoussein.cryptoapp.core.formatter

import kotlin.test.Test
import kotlin.test.assertEquals

class CommonPercentFormatterTest {

    private val percentFormatter = getPercentFormatter("en")

    @Test
    fun should_format_a_percent_without_fraction() {
        assertEquals("41%", percentFormatter(0.41))
    }

    @Test
    fun should_format_a_percent_with_1_fractions() {
        assertEquals("12.3%", percentFormatter(0.123))
    }

    @Test
    fun should_format_a_percent_with_2_fractions() {
        assertEquals("12.3%", percentFormatter(0.1234))
    }
}
