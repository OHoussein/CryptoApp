package dev.ohoussein.cryptoapp.core.formatter

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class AndroidPriceFormatter(private val localeId: String) : PriceFormatter {

    override fun invoke(price: Double, currencyCode: String): String {
        val local = Locale.forLanguageTag(localeId)
        return NumberFormat.getCurrencyInstance(local).run {
            currency = Currency.getInstance(currencyCode)
            minimumFractionDigits = 0
            format(price)
        }
    }
}

actual fun getPriceFormatter(localeId: String): PriceFormatter = AndroidPriceFormatter(localeId)
