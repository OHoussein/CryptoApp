package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import kotlin.math.roundToInt

class FakePriceFormatter : PriceFormatter {
    override fun invoke(price: Double, currencyCode: String): String {
        return "${price.roundToInt()} $currencyCode"
    }
}
