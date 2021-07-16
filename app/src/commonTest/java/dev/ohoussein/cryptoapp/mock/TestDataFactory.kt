@file:Suppress("MemberVisibilityCanBePrivate")

package dev.ohoussein.cryptoapp.mock

import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import java.util.concurrent.atomic.AtomicLong
import kotlin.random.Random

object TestDataFactory {
    private val idIndex = AtomicLong()

    fun randomCrypto(suffix: String) = DomainCrypto(
        id = idIndex.getAndIncrement().toString(),
        name = "Crypto $suffix",
        imageUrl = "Http://$suffix.com",
        symbol = suffix,
        price = Random.nextDouble(),
        priceChangePercentIn24h = Random.nextDouble(-12.0, 12.0),
    )

    fun makeCryptoList(count: Int): List<DomainCrypto> = (0..count).map {
        randomCrypto(it.toString())
    }
}