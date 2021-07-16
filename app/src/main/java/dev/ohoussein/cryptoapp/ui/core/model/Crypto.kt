package dev.ohoussein.cryptoapp.ui.core.model

data class Crypto(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: CryptoPrice,
    val priceChangePercentIn24h: LabelValue<Double>?,
)

data class CryptoPrice(
    val labelValue: LabelValue<Double>,
    val vsCurrencyCode: String,
)

data class LabelValue<V>(
    val value: V,
    val label: String,
)

