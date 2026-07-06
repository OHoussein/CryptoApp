package dev.ohoussein.cryptoapp.crypto.presentation.core

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.number

@Suppress("LongParameterList")
fun LocalDateTime.copy(
    year: Int = this.year,
    monthNumber: Int = this.month.number,
    dayOfMonth: Int = this.day,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
    nanosecond: Int = this.nanosecond,
) = LocalDateTime(
    year = year,
    month = Month(monthNumber),
    day = dayOfMonth,
    hour = hour,
    minute = minute,
    second = second,
    nanosecond = nanosecond
)
