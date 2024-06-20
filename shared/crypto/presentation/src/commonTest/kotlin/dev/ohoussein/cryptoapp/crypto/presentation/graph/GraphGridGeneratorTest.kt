package dev.ohoussein.cryptoapp.crypto.presentation.graph

import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.model.defaultLocale
import dev.ohoussein.cryptoapp.crypto.presentation.fake.FakePriceFormatter
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class GraphGridGeneratorTest {

    private val priceFormatter: PriceFormatter = FakePriceFormatter()
    private val graphGridGenerator
        get() = GraphGridGenerator(
            priceFormatter = priceFormatter,
            locale = defaultLocale,
            timeZone = TimeZone.UTC,
        )

    @Test
    fun `Given daily interval When getTimeGridInstants it return the correct time grid points`() {
        // Given
        val prices = listOf(
            HistoricalPrice(Instant.parse("2024-06-01T00:00:00Z").toEpochMilliseconds(), 100.0),
            HistoricalPrice(Instant.parse("2024-06-01T05:05:50Z").toEpochMilliseconds(), 150.0),
            HistoricalPrice(Instant.parse("2024-06-01T12:10:40Z").toEpochMilliseconds(), 200.0),
            HistoricalPrice(Instant.parse("2024-06-01T20:40:40Z").toEpochMilliseconds(), 160.0),
        )
        val countValues = 5
        val timeInterval = GraphInterval.INTERVAL_1_DAY

        // When
        val gridPoints = graphGridGenerator.getTimeGridInstants(prices, countValues, timeInterval)

        // Then
        gridPoints.map { it.label }.let(::println)
        assertEquals(countValues, gridPoints.size)
        assertEquals("00:00", gridPoints[0].label)
        assertEquals("05:00", gridPoints[1].label)
        assertEquals("10:00", gridPoints[2].label)
        assertEquals("15:00", gridPoints[3].label)
        assertEquals("20:40", gridPoints[4].label)
    }

    @Test
    fun `Given weekly interval When getTimeGridInstants it return the correct time grid points`() {
        // Given
        val prices = listOf(
            HistoricalPrice(Instant.parse("2024-06-10T20:49:27Z").toEpochMilliseconds(), 100.0),
            HistoricalPrice(Instant.parse("2024-06-14T20:22:25Z").toEpochMilliseconds(), 200.0),
            HistoricalPrice(Instant.parse("2024-06-17T20:22:25Z").toEpochMilliseconds(), 160.0),
        )
        val countValues = 4
        val timeInterval = GraphInterval.INTERVAL_7_DAYS

        // When
        val gridPoints = graphGridGenerator.getTimeGridInstants(prices, countValues, timeInterval)

        // Then
        gridPoints.map { it.label }.let(::println)
        assertEquals(countValues, gridPoints.size)
        assertEquals("06-10", gridPoints[0].label)
        assertEquals("06-13", gridPoints[1].label)
        assertEquals("06-15", gridPoints[2].label)
        assertEquals("06-17", gridPoints[3].label)
    }

    @Test
    fun `Given 1 year interval When getTimeGridInstants it return the correct time grid points`() {
        // Given
        val prices = listOf(
            HistoricalPrice(Instant.parse("2023-06-10T20:49:27Z").toEpochMilliseconds(), 100.0),
            HistoricalPrice(Instant.parse("2024-06-14T20:22:25Z").toEpochMilliseconds(), 200.0),
            HistoricalPrice(Instant.parse("2024-06-17T20:22:25Z").toEpochMilliseconds(), 160.0),
        )
        val countValues = 4
        val timeInterval = GraphInterval.INTERVAL_7_DAYS

        // When
        val gridPoints = graphGridGenerator.getTimeGridInstants(prices, countValues, timeInterval)

        // Then
        assertEquals(countValues, gridPoints.size)
        assertEquals("06-10", gridPoints[0].label)
        assertEquals("10-01", gridPoints[1].label)
        assertEquals("02-01", gridPoints[2].label)
        assertEquals("06-17", gridPoints[3].label)
    }

    @Test
    fun `Given prices When getPriceGridInstants it return the correct time grid points`() {
        // Given
        val prices = listOf(
            HistoricalPrice(Instant.parse("2023-06-10T20:49:27Z").toEpochMilliseconds(), 100.0),
            HistoricalPrice(Instant.parse("2024-06-14T20:22:25Z").toEpochMilliseconds(), 200.0),
            HistoricalPrice(Instant.parse("2024-06-17T20:22:25Z").toEpochMilliseconds(), 160.0),
        )
        val countValues = 4

        // When
        val gridPoints = graphGridGenerator.getPriceGridInstants(prices, countValues)

        // Then
        gridPoints.map { it.label }.forEach {
            println(it)
        }
        assertEquals(countValues, gridPoints.size)
        assertEquals("100 USD", gridPoints[0].label)
        assertEquals("133 USD", gridPoints[1].label)
        assertEquals("167 USD", gridPoints[2].label)
        assertEquals("200 USD", gridPoints[3].label)
    }
}
