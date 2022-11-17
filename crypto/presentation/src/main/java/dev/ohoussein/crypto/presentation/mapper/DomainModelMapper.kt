package dev.ohoussein.crypto.presentation.mapper

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.model.Locale
import dev.ohoussein.crypto.presentation.model.BaseCrypto
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.model.CryptoPrice
import dev.ohoussein.crypto.presentation.model.LabelValue
import dev.ohoussein.cryptoapp.core.formatter.PercentFormatter
import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter

class DomainModelMapper constructor(
    private val priceFormatter: PriceFormatter,
    private val percentFormatter: PercentFormatter,
    private val locale: Locale,
) {
    fun convert(domain: List<DomainCrypto>): List<Crypto> {
        return domain.map { convert(it) }
    }

    @Suppress("MagicNumber")
    private fun convert(domain: DomainCrypto): Crypto {
        return Crypto(
            base = BaseCrypto(
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

    fun convert(domain: DomainCryptoDetails) =
        CryptoDetails(
            base = BaseCrypto(
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
