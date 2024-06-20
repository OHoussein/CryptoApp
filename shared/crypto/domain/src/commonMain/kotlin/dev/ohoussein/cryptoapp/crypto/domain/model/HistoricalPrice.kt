package dev.ohoussein.cryptoapp.crypto.domain.model

data class HistoricalPrice(
    val timestampMillis: Long,
    val price: Double,
)
