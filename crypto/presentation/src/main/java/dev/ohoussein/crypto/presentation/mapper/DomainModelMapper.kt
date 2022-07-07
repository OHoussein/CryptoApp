package dev.ohoussein.crypto.presentation.mapper

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.presentation.model.BaseCrypto
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.model.CryptoPrice
import dev.ohoussein.crypto.presentation.model.LabelValue
import dev.ohoussein.cryptoapp.presentation.di.DIConstants.Qualifier.PERCENT_FORMATTER
import dev.ohoussein.cryptoapp.presentation.di.DIConstants.Qualifier.PRICE_FORMATTER
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DomainModelMapper @Inject constructor(
    @Named(PRICE_FORMATTER) private val priceFormatter: NumberFormat,
    @Named(PERCENT_FORMATTER) private val percentFormatter: NumberFormat,
) {
    fun convert(domain: List<DomainCrypto>, vsCurrencyCode: String): List<Crypto> {
        return domain.map { convert(it, vsCurrencyCode) }
    }

    @Suppress("MagicNumber")
    fun convert(domain: DomainCrypto, vsCurrencyCode: String): Crypto {
        priceFormatter.currency = Currency.getInstance(vsCurrencyCode)
        return Crypto(
            base = BaseCrypto(
                id = domain.id,
                name = domain.name,
                imageUrl = domain.imageUrl,
                symbol = domain.symbol.uppercase(),
            ),
            price = CryptoPrice(
                labelValue = LabelValue(domain.price, priceFormatter.format(domain.price)),
                vsCurrencyCode = vsCurrencyCode,
            ),
            priceChangePercentIn24h = domain.priceChangePercentIn24h?.let {
                LabelValue(it, percentFormatter.format(it / 100.0))
            },
        )
    }

    fun convert(domain: DomainCryptoDetails) =
        dev.ohoussein.crypto.presentation.model.CryptoDetails(
            base = dev.ohoussein.crypto.presentation.model.BaseCrypto(
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
                dev.ohoussein.crypto.presentation.model.LabelValue(it, percentFormatter.format(it))
            },
            sentimentDownVotesPercentage = domain.sentimentDownVotesPercentage?.let {
                dev.ohoussein.crypto.presentation.model.LabelValue(it, percentFormatter.format(it))
            },
            description = domain.description,
        )
}
