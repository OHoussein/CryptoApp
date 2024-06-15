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
    fun historicalPrices(): List<HistoricalPrice> = listOf(
        HistoricalPrice(1711843200000, 69702.3087473573),
        HistoricalPrice(1711929600000, 71246.95144060145),
        HistoricalPrice(1711983682000, 68887.74951585678),
    )
}
