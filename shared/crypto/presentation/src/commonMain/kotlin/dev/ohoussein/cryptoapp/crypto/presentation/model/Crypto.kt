package dev.ohoussein.cryptoapp.crypto.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class CryptoInfo(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
)

@Immutable
data class Crypto(
    val info: CryptoInfo,
    val price: CryptoPrice,
    val priceChangePercentIn24h: LabelValue<Double>?,
)

@Immutable
data class CryptoPrice(
    val labelValue: LabelValue<Double>,
)

@Immutable
data class LabelValue<V>(
    val value: V,
    val label: String,
)

@Immutable
data class CryptoDetails(
    val base: CryptoInfo,
    val hashingAlgorithm: String?,
    val homePageUrl: String?,
    val blockchainSite: String?,
    val mainRepoUrl: String?,
    val sentimentUpVotesPercentage: LabelValue<Double>?,
    val sentimentDownVotesPercentage: LabelValue<Double>?,
    val description: String,
)
