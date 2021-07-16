@file:Suppress("MagicNumber", "MayBeConstant")
package dev.ohoussein.cryptoapp.debug

import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.CryptoPrice
import dev.ohoussein.cryptoapp.ui.core.model.LabelValue

object DataPreview {
    val aa =""
    val previewCrypto by lazy {
        Crypto(
            id = "bitcoin",
            name = "Bitcoin",
            symbol = "BTC",
            imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            price = CryptoPrice(
                LabelValue(31827.0, "$31 827"),
                vsCurrencyCode = "USD",
            ),
            priceChangePercentIn24h = LabelValue(1.13, "1.13%")
        )
    }

    val previewListCrypto by lazy {
        (0..20).map { previewCrypto }
    }
}
