package dev.ohoussein.cryptoapp.data.mapper

import dev.ohoussein.cryptoapp.data.database.DBCrypto
import dev.ohoussein.cryptoapp.data.model.CryptoDetailsResponse
import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import java.util.Locale

class DomainModelMapper(private val locale: Locale) {

    fun convert(data: List<TopCryptoResponse>): List<DomainCrypto> =
        data.mapIndexed { index, item -> convert(item, index) }

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
        mainRepoUrl = data.links.reposUrl.firstNotNullOfOrNull { entry -> entry.value.firstOrNull { it.isNotEmpty() } },
        sentimentUpVotesPercentage = data.sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = data.sentimentDownVotesPercentage,
        description = data.description[locale.language] ?: data.description[Locale.ENGLISH.language]
        ?: "",
    )

    fun convertDBCrypto(data: List<DBCrypto>): List<DomainCrypto> = data.map { convert(it) }

    fun convert(data: DBCrypto) = DomainCrypto(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.imageUrl,
        price = data.price,
        priceChangePercentIn24h = data.priceChangePercentIn24h,
        order = data.order,
    )

    fun toDB(domain: List<DomainCrypto>) = domain.map { toDB(it) }

    fun toDB(domain: DomainCrypto) = DBCrypto(
        id = domain.id,
        symbol = domain.symbol,
        name = domain.name,
        imageUrl = domain.imageUrl,
        price = domain.price,
        priceChangePercentIn24h = domain.priceChangePercentIn24h,
        order = domain.order,
    )
}
