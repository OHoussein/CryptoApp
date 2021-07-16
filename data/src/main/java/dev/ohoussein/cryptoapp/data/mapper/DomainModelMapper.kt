package dev.ohoussein.cryptoapp.data.mapper

import dev.ohoussein.cryptoapp.data.model.TopCryptoResponse
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto

class DomainModelMapper {

    fun convert(data: List<TopCryptoResponse>): List<DomainCrypto> = data.map { convert(it) }

    fun convert(data: TopCryptoResponse) = DomainCrypto(
        id = data.id,
        symbol = data.symbol,
        name = data.name,
        imageUrl = data.image,
        price = data.currentPrice,
        priceChangePercentIn24h = data.priceChangePercentIn24h
    )
}
