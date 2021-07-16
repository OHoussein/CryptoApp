package dev.ohoussein.cryptoapp.ui.core.mapper

import androidx.compose.ui.text.toUpperCase
import dev.ohoussein.cryptoapp.di.DIConstants.Qualifier.PERCENT_FORMATTER
import dev.ohoussein.cryptoapp.di.DIConstants.Qualifier.PRICE_FORMATTER
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.CryptoPrice
import dev.ohoussein.cryptoapp.ui.core.model.LabelValue
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
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
            id = domain.id,
            name = domain.name,
            imageUrl = domain.imageUrl,
            symbol = domain.symbol.uppercase(),
            price = CryptoPrice(
                labelValue = LabelValue(domain.price, priceFormatter.format(domain.price)),
                vsCurrencyCode = vsCurrencyCode,
            ),
            priceChangePercentIn24h = domain.priceChangePercentIn24h?.let {
                LabelValue(it, percentFormatter.format(it/100.0))
            },
        )
    }
}