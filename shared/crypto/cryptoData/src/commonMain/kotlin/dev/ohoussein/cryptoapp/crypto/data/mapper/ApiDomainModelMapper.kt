package dev.ohoussein.cryptoapp.crypto.data.mapper

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.data.network.crypto.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.network.crypto.model.TopCryptoResponse

class ApiDomainModelMapper constructor(private val locale: Locale) {

    fun convert(data: List<TopCryptoResponse>): CryptoList =
        CryptoList(data.mapIndexed { index, item -> convert(item, index) })

    fun convert(data: TopCryptoResponse, index: Int) = DomainCrypto(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.image,
        price = data.currentPrice,
        priceChangePercentIn24h = data.priceChangePercentIn24h,
        order = index,
    )

    fun convert(data: CryptoDetailsResponse) = DomainCryptoDetails(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.image.large,
        hashingAlgorithm = data.hashingAlgorithm,
        homePageUrl = data.links.homepage.firstOrNull(),
        blockchainSite = data.links.blockchainSite.firstOrNull(),
        mainRepoUrl = data.links.reposUrl.firstNotNullOfOrNull { entry ->
            entry.value.firstOrNull { it.isNotEmpty() }
        },
        sentimentUpVotesPercentage = data.sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = data.sentimentDownVotesPercentage,
        description = data.description[locale.languageCode]
            ?: data.description["en"] ?: "",
    )
}
