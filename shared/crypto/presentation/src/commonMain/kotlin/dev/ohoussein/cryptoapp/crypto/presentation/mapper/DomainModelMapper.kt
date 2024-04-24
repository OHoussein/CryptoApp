package dev.ohoussein.cryptoapp.crypto.presentation.mapper

import dev.ohoussein.cryptoapp.core.formatter.PercentFormatter
import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoListModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoInfo
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoPrice
import dev.ohoussein.cryptoapp.crypto.presentation.model.LabelValue

class DomainModelMapper(
    private val priceFormatter: PriceFormatter,
    private val percentFormatter: PercentFormatter,
    private val locale: Locale,
) {
    fun convert(domain: CryptoListModel): List<Crypto> {
        return domain.list.map { convert(it) }
    }

    @Suppress("MagicNumber")
    private fun convert(domain: CryptoModel): Crypto {
        return Crypto(
            info = CryptoInfo(
                id = domain.id,
                name = domain.name,
                imageUrl = domain.imageUrl,
                symbol = domain.symbol.uppercase(),
            ),
            price = CryptoPrice(
                labelValue = LabelValue(domain.price, priceFormatter(domain.price, locale.currencyCode))
            ),
            priceChangePercentIn24h = domain.priceChangePercentIn24h?.let {
                LabelValue(it, percentFormatter(it / 100.0))
            },
        )
    }

    fun convert(domain: CryptoDetailsModel) =
        CryptoDetails(
            base = CryptoInfo(
                id = domain.id,
                name = domain.name,
                symbol = domain.symbol.uppercase(),
                imageUrl = domain.imageUrl,
            ),
            hashingAlgorithm = domain.hashingAlgorithm,
            homePageUrl = domain.homePageUrl,
            blockchainSite = domain.blockchainSite,
            mainRepoUrl = domain.mainRepoUrl,
            sentimentUpVotesPercentage = domain.sentimentUpVotesPercentage?.let {
                LabelValue(it, percentFormatter(it))
            },
            sentimentDownVotesPercentage = domain.sentimentDownVotesPercentage?.let {
                LabelValue(it, percentFormatter(it))
            },
            description = domain.description,
        )
}
