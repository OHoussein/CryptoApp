package dev.ohoussein.core.formatter

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import javax.inject.Inject

class PriceFormatter @Inject constructor(private val locale: Locale) {

    operator fun invoke(price: Double, currencyCode: String): String {
        return NumberFormat.getCurrencyInstance(locale).run {
            currency = Currency.getInstance(currencyCode)
            format(price)
        }
    }
}
