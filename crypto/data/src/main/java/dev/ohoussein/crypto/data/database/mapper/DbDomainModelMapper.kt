package dev.ohoussein.crypto.data.database.mapper

import dev.ohoussein.crypto.data.database.DBCrypto
import dev.ohoussein.crypto.domain.model.DomainCrypto

class DbDomainModelMapper {

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
