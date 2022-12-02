package dev.ohoussein.cryptoapp.core.formatter

import java.text.NumberFormat
import java.util.Locale

class AndroidPercentFormatter(private val localeId: String) : PercentFormatter {

    override operator fun invoke(percent: Double): String {
        val local = Locale.forLanguageTag(localeId)
        return NumberFormat.getPercentInstance(local).run {
            minimumFractionDigits = 0
            maximumFractionDigits = 1
            format(percent)
        }
    }
}

actual fun getPercentFormatter(localeId: String): PercentFormatter = AndroidPercentFormatter(localeId)
