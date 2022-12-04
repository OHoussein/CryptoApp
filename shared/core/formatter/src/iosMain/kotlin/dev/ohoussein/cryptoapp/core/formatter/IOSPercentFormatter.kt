package dev.ohoussein.cryptoapp.core.formatter

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterPercentStyle

class IOSPercentFormatter(private val localeId: String) : PercentFormatter {

    override operator fun invoke(percent: Double): String {
        return NSNumberFormatter().run {
            numberStyle = NSNumberFormatterPercentStyle
            locale = NSLocale(localeId)
            minimumFractionDigits = 0u
            maximumFractionDigits = 1u
            stringFromNumber(NSNumber(percent))!!
        }
    }
}

actual fun getPercentFormatter(localeId: String): PercentFormatter = IOSPercentFormatter(localeId)
