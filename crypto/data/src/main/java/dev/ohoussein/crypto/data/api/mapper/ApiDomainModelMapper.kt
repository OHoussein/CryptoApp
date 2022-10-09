package dev.ohoussein.crypto.data.api.mapper

import dev.ohoussein.crypto.data.api.CryptoDetailsResponse
import dev.ohoussein.crypto.data.api.TopCryptoResponse
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.data.database.DBCrypto
import java.util.*

class ApiDomainModelMapper(private val locale: Locale) {

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
            mainRepoUrl = data.links.reposUrl.firstNotNullOfOrNull { entry ->
                entry.value.firstOrNull { it.isNotEmpty() }
            },
            sentimentUpVotesPercentage = data.sentimentUpVotesPercentage,
            sentimentDownVotesPercentage = data.sentimentDownVotesPercentage,
            description = data.description[locale.language]
                    ?: data.description[Locale.ENGLISH.language] ?: "",
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
}
