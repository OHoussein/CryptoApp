@file:Suppress("MatchingDeclarationName")

package dev.ohoussein.cryptoapp.data.database

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "crypto")
data class DBCrypto(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val symbol: String,
    val priceChangePercentIn24h: Double?,
    val order: Int,
)
