@file:Suppress("MemberVisibilityCanBePrivate", "MaxLineLength", "MagicNumber")

package dev.ohoussein.core.test.mock

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

object TestDataFactory {
    private val idIndex = AtomicLong()

    fun randomCrypto(suffix: String) = CryptoModel(
        id = idIndex.getAndIncrement().toString(),
        name = "Crypto $suffix",
        imageUrl = "Http://$suffix.com",
        symbol = suffix,
        price = Random.nextDouble(),
        priceChangePercentIn24h = Random.nextDouble(-12.0, 12.0),
        order = idIndex.toInt()
    )

    fun randomCryptoDetails(suffix: String): CryptoDetailsModel {
        val upVotesPercentage = Random.nextInt(1, 100).toDouble()
        return CryptoDetailsModel(
            id = idIndex.getAndIncrement().toString(),
            name = "Crypto $suffix",
            imageUrl = "",
            symbol = suffix,
            hashingAlgorithm = "SHA-256",
            homePageUrl = "Http://home-$suffix.com",
            mainRepoUrl = "Http://repo-$suffix.com",
            blockchainSite = "Http://blockchain-$suffix.com",
            sentimentUpVotesPercentage = upVotesPercentage,
            sentimentDownVotesPercentage = 100 - upVotesPercentage,
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
        )
    }

    fun makeCryptoList(count: Int): List<CryptoModel> = (0..count).map {
        randomCrypto(it.toString())
    }
}
