package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.core.formatter.PercentFormatter

class FakePercentFormatter : PercentFormatter {
    override fun invoke(percent: Double): String = "$percent%"
}
