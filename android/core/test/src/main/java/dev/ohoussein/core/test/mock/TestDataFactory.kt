@file:Suppress("MemberVisibilityCanBePrivate", "MaxLineLength", "MagicNumber")

package dev.ohoussein.core.test.mock

import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

object TestDataFactory {
    private val idIndex = AtomicLong()

    fun randomCrypto(suffix: String) = DomainCrypto(
        id = idIndex.getAndIncrement().toString(),
        name = "Crypto $suffix",
        imageUrl = "Http://$suffix.com",
        symbol = suffix,
        price = Random.nextDouble(),
        priceChangePercentIn24h = Random.nextDouble(-12.0, 12.0),
        order = idIndex.toInt()
    )

    fun randomCryptoDetails(suffix: String): DomainCryptoDetails {
        val upVotesPercentage = Random.nextInt(1, 100).toDouble()
        return DomainCryptoDetails(
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

    fun makeCryptoList(count: Int): List<DomainCrypto> = (0..count).map {
        randomCrypto(it.toString())
    }
}
