package dev.ohoussein.cryptoapp.crypto.presentation.graph

import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.presentation.core.copy
import dev.ohoussein.cryptoapp.crypto.presentation.core.interpolateValues
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import dev.ohoussein.cryptoapp.designsystem.graph.model.GridPoint
import kotlinx.datetime.*
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.milliseconds

private const val THRESHOLD_IGNORE_MINUTES_IN_DAYS = 2
private const val THRESHOLD_IGNORE_HOURS_IN_DAYS = 4 * 30

class GraphGridGenerator(
    private val priceFormatter: PriceFormatter,
    private val locale: Locale,
    private val timeZone: TimeZone = TimeZone.currentSystemDefault(),
) {

    @OptIn(FormatStringsInDatetimeFormats::class)
    fun getTimeGridInstants(
        prices: List<HistoricalPrice>,
        countValues: Int,
        timeInterval: GraphInterval,
    ): List<GridPoint> {
        val start = prices.first().timestampMillis
        val end = prices.last().timestampMillis

        return getTimeGridInstants(start, end, countValues).map { instant ->
            val date = instant.toLocalDateTime(timeZone)
            val string = date.format(LocalDateTime.Format { byUnicodePattern(timeInterval.timeFormat) })
            GridPoint(instant.toEpochMilliseconds().toDouble(), string)
        }
    }

    fun getPriceGridInstants(
        prices: List<HistoricalPrice>,
        countValues: Int,
    ): List<GridPoint> {
        return interpolateValues(
            start = prices.minOf { it.price },
            end = prices.maxOf { it.price },
            countValues = countValues
        ).map { price ->
            GridPoint(
                position = price,
                label = priceFormatter(price, locale.currencyCode),
            )
        }
    }

    private fun getTimeGridInstants(
        startEpochMilliseconds: Long,
        endEpochMilliseconds: Long,
        countValues: Int,
    ): List<Instant> {
        return interpolateValues(startEpochMilliseconds, endEpochMilliseconds, countValues)
            .mapIndexed { index, time ->
                val duration = (endEpochMilliseconds - startEpochMilliseconds).milliseconds
                when {
                    index == 0 -> Instant.fromEpochMilliseconds(startEpochMilliseconds)
                    index == countValues - 1 -> Instant.fromEpochMilliseconds(endEpochMilliseconds)

                    duration < THRESHOLD_IGNORE_MINUTES_IN_DAYS.days -> {
                        Instant.fromEpochMilliseconds(time)
                            .toLocalDateTime(timeZone)
                            .copy(minute = 0, second = 0, nanosecond = 0)
                            .toInstant(timeZone)
                    }

                    duration < THRESHOLD_IGNORE_HOURS_IN_DAYS.days -> {
                        Instant.fromEpochMilliseconds(time)
                            .toLocalDateTime(timeZone)
                            .copy(hour = 0, minute = 0, second = 0, nanosecond = 0)
                            .toInstant(timeZone)
                    }

                    else -> {
                        Instant.fromEpochMilliseconds(time)
                            .toLocalDateTime(timeZone)
                            .copy(dayOfMonth = 1, hour = 0, minute = 0, second = 0, nanosecond = 0)
                            .toInstant(timeZone)
                    }
                }
            }
    }

    private val GraphInterval.timeFormat: String
        get() = when (this) {
            GraphInterval.INTERVAL_1_DAY -> "HH:mm"
            else -> "MM-dd"
        }
}
