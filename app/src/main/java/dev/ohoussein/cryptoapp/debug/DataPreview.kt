@file:Suppress("MagicNumber", "MaxLineLength")

package dev.ohoussein.cryptoapp.debug

import dev.ohoussein.cryptoapp.ui.core.model.BaseCrypto
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.CryptoDetails
import dev.ohoussein.cryptoapp.ui.core.model.CryptoPrice
import dev.ohoussein.cryptoapp.ui.core.model.LabelValue

object DataPreview {
    val previewCrypto by lazy {
        Crypto(
            base = BaseCrypto(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            ),
            price = CryptoPrice(
                LabelValue(31827.0, "$31 827"),
                vsCurrencyCode = "USD",
            ),
            priceChangePercentIn24h = LabelValue(1.13, "1.13%")
        )
    }

    val previewCryptoDetails by lazy {
        CryptoDetails(
            base = BaseCrypto(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            ),
            hashingAlgorithm = "SHA-256",
            homePageUrl = "http://www.bitcoin.org",
            blockchainSite = "https://btc.com/",
            mainRepoUrl = "https://github.com/bitcoin/bitcoin",
            sentimentUpVotesPercentage = LabelValue(64.0, "64 %"),
            sentimentDownVotesPercentage = LabelValue(37.0, "37 %"),
            description = "<b>Lorem ipsum dolor sit amet</b>, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        )
    }

    val previewListCrypto by lazy {
        (0..20).map { previewCrypto }
    }
}
