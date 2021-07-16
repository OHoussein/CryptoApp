package dev.ohoussein.cryptoapp.data.model

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
