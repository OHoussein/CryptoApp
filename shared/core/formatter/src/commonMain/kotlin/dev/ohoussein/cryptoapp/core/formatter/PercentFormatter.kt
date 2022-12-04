package dev.ohoussein.cryptoapp.core.formatter

interface PercentFormatter {
    operator fun invoke(percent: Double): String
}

expect fun getPercentFormatter(localeId: String): PercentFormatter
