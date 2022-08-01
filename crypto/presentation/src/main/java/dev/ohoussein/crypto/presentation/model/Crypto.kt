package dev.ohoussein.crypto.presentation.model

data class BaseCrypto(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
)

data class Crypto(
    val base: BaseCrypto,
    val price: CryptoPrice,
    val priceChangePercentIn24h: LabelValue<Double>?,
)

data class CryptoPrice(
    val labelValue: LabelValue<Double>,
)

data class LabelValue<V>(
    val value: V,
    val label: String,
)

data class CryptoDetails(
    val base: BaseCrypto,
    val hashingAlgorithm: String?,
    val homePageUrl: String?,
    val blockchainSite: String?,
    val mainRepoUrl: String?,
    val sentimentUpVotesPercentage: LabelValue<Double>?,
    val sentimentDownVotesPercentage: LabelValue<Double>?,
    val description: String,
)
