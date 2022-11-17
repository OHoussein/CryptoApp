package dev.ohoussein.cryptoapp.core.formatter

import java.text.NumberFormat
import java.util.Currency

class PriceFormatter {

    operator fun invoke(price: Double, currencyCode: String): String {
        return NumberFormat.getCurrencyInstance().run {
            currency = Currency.getInstance(currencyCode)
            format(price)
        }
    }
}
