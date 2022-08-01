@file:Suppress("MemberVisibilityCanBePrivate", "MaxLineLength", "MagicNumber")

package dev.ohoussein.cryptoapp.data.database.mock

import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

object TestDataFactory {
    private val idIndex = AtomicLong()

    fun randomDBCrypto(suffix: String) = DBCrypto(
        id = idIndex.getAndIncrement().toString(),
        name = "Crypto $suffix",
        imageUrl = "Http://$suffix.com",
        symbol = suffix,
        price = Random.nextDouble(),
        priceChangePercentIn24h = Random.nextDouble(-12.0, 12.0),
        order = idIndex.toInt()
    )

    fun makeDBCryptoList(count: Int): List<DBCrypto> = (0..count).map {
        randomDBCrypto(it.toString())
    }
}
