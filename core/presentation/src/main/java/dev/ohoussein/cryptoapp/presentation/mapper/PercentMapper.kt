package dev.ohoussein.cryptoapp.presentation.mapper

import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

class PercentMapper @Inject constructor(private val locale: Locale) {

    operator fun invoke(value: Double): String {
        return NumberFormat.getPercentInstance(locale).run {
            minimumFractionDigits = 2
            format(value)
        }
    }
}
