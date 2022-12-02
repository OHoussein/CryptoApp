package dev.ohoussein.cryptoapp.core.formatter

interface PriceFormatter {
    operator fun invoke(price: Double, currencyCode: String): String
}

expect fun getPriceFormatter(localeId: String): PriceFormatter
