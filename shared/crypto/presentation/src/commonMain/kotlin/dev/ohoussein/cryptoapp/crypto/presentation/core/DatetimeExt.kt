package dev.ohoussein.cryptoapp.crypto.presentation.core

import kotlinx.datetime.LocalDateTime

@Suppress("LongParameterList")
fun LocalDateTime.copy(
    year: Int = this.year,
    monthNumber: Int = this.monthNumber,
    dayOfMonth: Int = this.dayOfMonth,
    hour: Int = this.hour,
    minute: Int = this.minute,
    second: Int = this.second,
    nanosecond: Int = this.nanosecond,
) = LocalDateTime(
    year = year,
    monthNumber = monthNumber,
    dayOfMonth = dayOfMonth,
    hour = hour,
    minute = minute,
    second = second,
    nanosecond = nanosecond
)
