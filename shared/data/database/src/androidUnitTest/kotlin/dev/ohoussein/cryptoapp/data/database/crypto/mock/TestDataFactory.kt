package dev.ohoussein.cryptoapp.data.database.crypto.mock

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
        order = idIndex.toInt(),
        sparkLine7d = null,
    )

    fun makeCryptoList(count: Int) = (0..count).map {
        randomCrypto(it.toString())
    }

    fun randomCryptoDetails(
        suffix: String,
        id: String = idIndex.getAndIncrement().toString(),
    ) = CryptoDetailsModel(
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
