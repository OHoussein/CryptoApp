package dev.ohoussein.cryptoapp.core.formatter

import java.text.NumberFormat

class PercentFormatter {

    operator fun invoke(value: Double): String {
        return NumberFormat.getPercentInstance().run {
            minimumFractionDigits = 2
            format(value)
        }
    }
}
