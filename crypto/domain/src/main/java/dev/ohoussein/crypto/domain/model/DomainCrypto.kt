package dev.ohoussein.crypto.domain.model

data class DomainCrypto(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val symbol: String,
    val priceChangePercentIn24h: Double?,
    val order: Int,
)

data class DomainCryptoDetails(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val hashingAlgorithm: String?,
    val homePageUrl: String?,
    val blockchainSite: String?,
    val mainRepoUrl: String?,
    val sentimentUpVotesPercentage: Double?,
    val sentimentDownVotesPercentage: Double?,
    val description: String,
)
