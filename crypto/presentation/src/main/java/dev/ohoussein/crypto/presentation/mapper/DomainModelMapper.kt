package dev.ohoussein.crypto.presentation.mapper

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.presentation.model.BaseCrypto
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.model.CryptoPrice
import dev.ohoussein.crypto.presentation.model.LabelValue
import dev.ohoussein.cryptoapp.presentation.mapper.PercentMapper
import dev.ohoussein.cryptoapp.presentation.mapper.PriceMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DomainModelMapper @Inject constructor(
    private val priceMapper: PriceMapper,
    private val percentMapper: PercentMapper,
) {
    fun convert(domain: List<DomainCrypto>, vsCurrencyCode: String): List<Crypto> {
        return domain.map { convert(it, vsCurrencyCode) }
    }

    @Suppress("MagicNumber")
    fun convert(domain: DomainCrypto, vsCurrencyCode: String): Crypto {
        return Crypto(
            base = BaseCrypto(
                id = domain.id,
                name = domain.name,
                imageUrl = domain.imageUrl,
                symbol = domain.symbol.uppercase(),
            ),
            price = CryptoPrice(
                labelValue = LabelValue(domain.price, priceMapper(domain.price, vsCurrencyCode)),
                vsCurrencyCode = vsCurrencyCode,
            ),
            priceChangePercentIn24h = domain.priceChangePercentIn24h?.let {
                LabelValue(it, percentMapper(it / 100.0))
            },
        )
    }

    fun convert(domain: DomainCryptoDetails) =
        dev.ohoussein.crypto.presentation.model.CryptoDetails(
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
                LabelValue(it, percentMapper(it))
            },
            sentimentDownVotesPercentage = domain.sentimentDownVotesPercentage?.let {
                LabelValue(it, percentMapper(it))
            },
            description = domain.description,
        )
}
