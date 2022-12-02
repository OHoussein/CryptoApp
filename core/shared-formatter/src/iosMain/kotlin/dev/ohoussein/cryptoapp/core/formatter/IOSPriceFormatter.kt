package dev.ohoussein.cryptoapp.core.formatter

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

class IOSPriceFormatter(private val localeId: String) : PriceFormatter {

    override fun invoke(price: Double, currencyCode: String): String {
        return NSNumberFormatter().run {
            numberStyle = NSNumberFormatterCurrencyStyle
            locale = NSLocale(localeId)
            minimumFractionDigits = 0u
            this.currencyCode = currencyCode
            stringFromNumber(NSNumber(price))!!
        }
    }
}

actual fun getPriceFormatter(localeId: String): PriceFormatter = IOSPriceFormatter(localeId)
