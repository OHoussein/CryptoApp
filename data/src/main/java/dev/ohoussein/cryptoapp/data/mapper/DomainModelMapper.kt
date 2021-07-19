package dev.ohoussein.cryptoapp.data.mapper

import dev.ohoussein.cryptoapp.data.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import java.util.Locale

class DomainModelMapper(private val locale: Locale) {

    fun convert(data: List<TopCryptoResponse>): List<DomainCrypto> = data.map { convert(it) }

    fun convert(data: TopCryptoResponse) = DomainCrypto(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.image,
        price = data.currentPrice,
        priceChangePercentIn24h = data.priceChangePercentIn24h
    )

    fun convert(data: CryptoDetailsResponse) = DomainCryptoDetails(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.image.large,
        hashingAlgorithm = data.hashingAlgorithm,
        homePageUrl = data.links.homepage.firstOrNull(),
        blockchainSite = data.links.blockchainSite.firstOrNull(),
        mainRepoUrl = data.links.reposUrl.firstNotNullOfOrNull { entry -> entry.value.firstOrNull { it.isNotEmpty() } },
        sentimentUpVotesPercentage = data.sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = data.sentimentDownVotesPercentage,
        description = data.description[locale.language] ?: data.description[Locale.ENGLISH.language]
        ?: "",
    )
}
