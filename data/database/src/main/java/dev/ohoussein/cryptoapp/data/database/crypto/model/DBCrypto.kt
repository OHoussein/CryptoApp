@file:Suppress("MatchingDeclarationName")

package dev.ohoussein.cryptoapp.data.database.crypto.model

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

@Keep
@Entity(tableName = "crypto_details")
data class DBCryptoDetails(
    @PrimaryKey val id: String,
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
