package dev.ohoussein.cryptoapp.core.formatter

import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class PercentFormatter @Inject constructor(private val locale: Locale) {

    operator fun invoke(value: Double): String {
        return NumberFormat.getPercentInstance(locale).run {
            minimumFractionDigits = 2
            format(value)
        }
    }
}
