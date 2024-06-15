package dev.ohoussein.cryptoapp.crypto.domain.model

object FakeCryptoModel {

    fun crypto(
        id: String = "bitcoin",
        order: Int = 1,
    ) = CryptoModel(
        id = id,
        name = "crypto $id",
        imageUrl = "https://$id.com",
        symbol = "CR-$id",
        price = 70.0,
        priceChangePercentIn24h = -2.0,
        order = order,
        sparkLine7d = null,
    )

    fun cryptoList(count: Int): List<CryptoModel> =
        (1..count).map {
            crypto(it.toString(), order = it)
        }

    fun cryptoDetails(
        id: String = "bitcoin",
    ) = CryptoDetailsModel(
        id = id,
        name = "crypto $id",
        imageUrl = "https://image-$id.com",
        symbol = "CR-$id",
        hashingAlgorithm = "SHA-256",
        homePageUrl = "http://home-$id.com",
        blockchainSite = "http://blockchain-$id.com",
        mainRepoUrl = "http://repo-$id.com",
        sentimentUpVotesPercentage = 22.0,
        sentimentDownVotesPercentage = 100 - 22.0,
        description = "details $id",
    )

    @Suppress("MagicNumber")
    fun historicalPrices(count: Int = 7): List<HistoricalPrice> = (1..count).map {
        HistoricalPrice(1711843200000 + it, 69702.0 + it * 1000)
    }
}
