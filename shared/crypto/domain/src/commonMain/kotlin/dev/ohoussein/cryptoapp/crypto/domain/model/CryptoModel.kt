@file:OptIn(ExperimentalObjCName::class)

package dev.ohoussein.cryptoapp.crypto.domain.model

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@ObjCName(name = "CryptoModel", exact = true)
data class CryptoModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val symbol: String,
    val priceChangePercentIn24h: Double?,
    val order: Int,
    val sparkLine7d: List<Double>?,
)

@ObjCName(name = "CryptoDetailsModel", exact = true)
data class CryptoDetailsModel(
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
