package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter

class FakePriceFormatter : PriceFormatter {
    override fun invoke(price: Double, currencyCode: String): String =
        "$price $currencyCode"
}