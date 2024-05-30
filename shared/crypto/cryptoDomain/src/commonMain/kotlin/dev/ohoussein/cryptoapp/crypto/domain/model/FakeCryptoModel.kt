package dev.ohoussein.cryptoapp.crypto.domain.model

import kotlin.random.Random

object FakeCryptoModel {

    fun crypto(
        id: String = "bitcoin",
        order: Int = 1,
    ) = CryptoModel(
        id = id,
        name = id,
        imageUrl = "Http://$id.com",
        symbol = id,
        price = Random.nextDouble(),
        priceChangePercentIn24h = Random.nextDouble(-12.0, 12.0),
        order = order,
    )

    fun cryptoList(count: Int): List<CryptoModel> =
        (1..count).map {
            crypto(it.toString(), order = it)
        }

    fun cryptoDetails(
        id: String = "bitcoin",
    ) = CryptoDetailsModel(
        id = id,
        name = id,
        imageUrl = "Http://image-$id.com",
        symbol = id,
        hashingAlgorithm = "SHA-256",
        homePageUrl = "http://home-$id.com",
        blockchainSite = "http://blockchain-$id.com",
        mainRepoUrl = "http://repo-$id.com",
        sentimentUpVotesPercentage = 22.0,
        sentimentDownVotesPercentage = 100 - 22.0,
        description = "details $id",
    )
}