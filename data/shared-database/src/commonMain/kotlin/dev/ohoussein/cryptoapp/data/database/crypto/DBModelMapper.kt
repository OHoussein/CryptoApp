package dev.ohoussein.cryptoapp.data.database.crypto

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.db.Crypto as DBCrypto
import dev.ohoussein.cryptoapp.db.CryptoDetails as DBCryptoDetails

class DBModelMapper {

    fun toDomainCrypto(
        id: String,
        name: String,
        imageUrl: String,
        price: Double,
        symbol: String,
        priceChangePercentIn24h: Double?,
        orderInList: Long,
    ) = DomainCrypto(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
        price = price,
        priceChangePercentIn24h = priceChangePercentIn24h,
        order = orderInList.toInt(),
    )

    fun toDB(domain: DomainCrypto) = DBCrypto(
        id = domain.id,
        symbol = domain.symbol,
        name = domain.name,
        imageUrl = domain.imageUrl,
        price = domain.price,
        priceChangePercentIn24h = domain.priceChangePercentIn24h,
        orderInList = domain.order.toLong(),
    )

    fun toDB(cryptoDetails: DomainCryptoDetails): DBCryptoDetails = DBCryptoDetails(
        id = cryptoDetails.id,
        name = cryptoDetails.name,
        symbol = cryptoDetails.symbol,
        imageUrl = cryptoDetails.imageUrl,
        hashingAlgorithm = cryptoDetails.hashingAlgorithm,
        homePageUrl = cryptoDetails.homePageUrl,
        blockchainSite = cryptoDetails.blockchainSite,
        mainRepoUrl = cryptoDetails.mainRepoUrl,
        sentimentUpVotesPercentage = cryptoDetails.sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = cryptoDetails.sentimentDownVotesPercentage,
        description = cryptoDetails.description,
    )

    fun toDomainCryptoDetails(
        id: String,
        name: String,
        imageUrl: String,
        symbol: String,
        hashingAlgorithm: String?,
        homePageUrl: String?,
        blockchainSite: String?,
        mainRepoUrl: String?,
        sentimentUpVotesPercentage: Double?,
        sentimentDownVotesPercentage: Double?,
        description: String,
    ) = DomainCryptoDetails(
        id = id,
        name = name,
        symbol = symbol,
        imageUrl = imageUrl,
        hashingAlgorithm = hashingAlgorithm,
        homePageUrl = homePageUrl,
        blockchainSite = blockchainSite,
        mainRepoUrl = mainRepoUrl,
        sentimentUpVotesPercentage = sentimentUpVotesPercentage,
        sentimentDownVotesPercentage = sentimentDownVotesPercentage,
        description = description,
    )
}
