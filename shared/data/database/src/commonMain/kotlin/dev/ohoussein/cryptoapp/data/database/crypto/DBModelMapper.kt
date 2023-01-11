package dev.ohoussein.cryptoapp.data.database.crypto

import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.db.Crypto as DBCrypto
import dev.ohoussein.cryptoapp.db.CryptoDetails as DBCryptoDetails

class DBModelMapper {

    fun toCryptoModel(
        id: String,
        name: String,
        imageUrl: String,
        price: Double,
        symbol: String,
        priceChangePercentIn24h: Double?,
        orderInList: Long,
    ) = CryptoModel(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
        price = price,
        priceChangePercentIn24h = priceChangePercentIn24h,
        order = orderInList.toInt(),
    )

    fun toDB(domain: CryptoModel) = DBCrypto(
        id = domain.id,
        symbol = domain.symbol,
        name = domain.name,
        imageUrl = domain.imageUrl,
        price = domain.price,
        priceChangePercentIn24h = domain.priceChangePercentIn24h,
        orderInList = domain.order.toLong(),
    )

    fun toDB(cryptoDetails: CryptoDetailsModel): DBCryptoDetails = DBCryptoDetails(
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

    fun toCryptoDetailsModel(
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
    ) = CryptoDetailsModel(
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
