package dev.ohoussein.cryptoapp.crypto.presentation.core

import kotlin.test.Test
import kotlin.test.assertEquals

class MathTest {

    @Test
    fun `Given values less than max value When call averageValues it it should return the same list`() {
        val prices = listOf(100.0, 110.0, 105.0)
        val result = prices.averageValues(maxValues = 20)
        val expected = listOf(100.0, 110.0, 105.0)

        assertEquals(expected, result)
    }

    @Test
    fun `Given values When call averageValues it it should return the average values`() {
        val prices = (1..40).map { it.toDouble() }
        val result = prices.averageValues(maxValues = 20)
        val expected = listOf(
            1.5, 3.5, 5.5, 7.5, 9.5, 11.5, 13.5, 15.5, 17.5, 19.5,
            21.5, 23.5, 25.5, 27.5, 29.5, 31.5, 33.5, 35.5, 37.5, 39.5
        )

        assertEquals(expected, result)
    }

    @Test
    fun `Given empty values When call averageValues it it should return an empty list`() {
        val prices = emptyList<Double>()
        val result = prices.averageValues(maxValues = 20)
        val expected = emptyList<Double>()

        assertEquals(expected, result)
    }
}
