@file:Suppress("MemberVisibilityCanBePrivate", "MaxLineLength", "MagicNumber")

package dev.ohoussein.cryptoapp.data.database.mock

import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCryptoDetails
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

object TestDataFactory {
    private val idIndex = AtomicLong()

    fun randomDBCrypto(suffix: String) = DBCrypto(
        id = idIndex.getAndIncrement().toString(),
        name = "Crypto $suffix",
        imageUrl = "Http://$suffix.com",
        symbol = suffix,
        price = Random.nextDouble(),
        priceChangePercentIn24h = Random.nextDouble(-12.0, 12.0),
        order = idIndex.toInt()
    )

    fun makeDBCryptoList(count: Int): List<DBCrypto> = (0..count).map {
        randomDBCrypto(it.toString())
    }

    fun randomDBCryptoDetails(
        suffix: String,
        id: String = idIndex.getAndIncrement().toString(),
    ) = DBCryptoDetails(
        id = id,
        name = "Crypto $suffix",
        imageUrl = "Http://image-$suffix.com",
        symbol = suffix,
        hashingAlgorithm = "SHA-256",
        homePageUrl = "http://home-$suffix.com",
        blockchainSite = "http://blockchain-$suffix.com",
        mainRepoUrl = "http://repo-$suffix.com",
        sentimentUpVotesPercentage = 22.0,
        sentimentDownVotesPercentage = 100 - 22.0,
        description = "details $suffix",
    )
}
