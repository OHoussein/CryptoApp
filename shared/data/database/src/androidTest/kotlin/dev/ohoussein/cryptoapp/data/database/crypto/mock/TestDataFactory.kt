package dev.ohoussein.cryptoapp.data.database.crypto.mock

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
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
        order = idIndex.toInt(),
    )

    fun makeCryptoList(count: Int) =
        CryptoList(
            list = (0..count).map {
                randomCrypto(it.toString())
            }
        )

    fun randomCryptoDetails(
        suffix: String,
        id: String = idIndex.getAndIncrement().toString(),
    ) = DomainCryptoDetails(
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
