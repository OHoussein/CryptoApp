package dev.ohoussein.crypto.data.api

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
data class TopCryptoResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "symbol") val symbol: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "image") val image: String,
    @field:Json(name = "current_price") val currentPrice: Double,
    @field:Json(name = "price_change_percentage_24h") val priceChangePercentIn24h: Double?,
)

@Keep
data class CryptoDetailsResponse(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "symbol") val symbol: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "hashing_algorithm") val hashingAlgorithm: String,
    @field:Json(name = "description") val description: Map<String, String>,
    @field:Json(name = "sentiment_votes_up_percentage") val sentimentUpVotesPercentage: Double,
    @field:Json(name = "sentiment_votes_down_percentage") val sentimentDownVotesPercentage: Double,
    @field:Json(name = "image") val image: CryptoImageResponse,
    @field:Json(name = "links") val links: CryptoLinksResponse,
)

@Keep
data class CryptoImageResponse(
    @field:Json(name = "thumb") val thumb: String,
    @field:Json(name = "small") val small: String,
    @field:Json(name = "large") val large: String,
)

@Keep
data class CryptoLinksResponse(
    @field:Json(name = "homepage") val homepage: List<String>,
    @field:Json(name = "blockchain_site") val blockchainSite: List<String>,
    @field:Json(name = "repos_url") val reposUrl: Map<String, List<String>>,
)
