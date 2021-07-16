package dev.ohoussein.cryptoapp.domain.model

data class DomainCrypto(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val symbol: String,
    val priceChangePercentIn24h: Double?,
)
