package dev.ohoussein.cryptoapp.data.network.crypto.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopCryptoResponse(
    @SerialName("id") val id: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("image") val image: String,
    @SerialName("current_price") val currentPrice: Double,
    @SerialName("price_change_percentage_24h") val priceChangePercentIn24h: Double?,
    @SerialName("sparkline_in_7d") val sparklineIn7d: SparkLineDTO? = null,
)

@Serializable
data class CryptoDetailsResponse(
    @SerialName("id") val id: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("name") val name: String,
    @SerialName("hashing_algorithm") val hashingAlgorithm: String?,
    @SerialName("description") val description: Map<String, String>,
    @SerialName("sentiment_votes_up_percentage") val sentimentUpVotesPercentage: Double,
    @SerialName("sentiment_votes_down_percentage") val sentimentDownVotesPercentage: Double,
    @SerialName("image") val image: CryptoImageResponse,
    @SerialName("links") val links: CryptoLinksResponse,
)

@Serializable
data class CryptoImageResponse(
    @SerialName("thumb") val thumb: String,
    @SerialName("small") val small: String,
    @SerialName("large") val large: String,
)

@Serializable
data class CryptoLinksResponse(
    @SerialName("homepage") val homepage: List<String>,
    @SerialName("blockchain_site") val blockchainSite: List<String>,
    @SerialName("repos_url") val reposUrl: Map<String, List<String>>,
)

@Serializable
data class SparkLineDTO(
    val price: List<Double>,
)

@Serializable
data class HistoricalPricesDTO(
    val prices: List<List<Double>>,
)
