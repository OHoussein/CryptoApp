@file:Suppress("MagicNumber", "MaxLineLength")

package dev.ohoussein.crypto.presentation.debug

object DataPreview {
    val previewCrypto by lazy {
        dev.ohoussein.crypto.presentation.model.Crypto(
                base = dev.ohoussein.crypto.presentation.model.BaseCrypto(
                        id = "bitcoin",
                        name = "Bitcoin",
                        symbol = "BTC",
                        imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                ),
                price = dev.ohoussein.crypto.presentation.model.CryptoPrice(
                        dev.ohoussein.crypto.presentation.model.LabelValue(31827.0, "$31 827"),
                        vsCurrencyCode = "USD",
                ),
                priceChangePercentIn24h = dev.ohoussein.crypto.presentation.model.LabelValue(1.13, "1.13%")
        )
    }

    val previewCryptoDetails by lazy {
        dev.ohoussein.crypto.presentation.model.CryptoDetails(
                base = dev.ohoussein.crypto.presentation.model.BaseCrypto(
                        id = "bitcoin",
                        name = "Bitcoin",
                        symbol = "BTC",
                        imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                ),
                hashingAlgorithm = "SHA-256",
                homePageUrl = "http://www.bitcoin.org",
                blockchainSite = "https://btc.com/",
                mainRepoUrl = "https://github.com/bitcoin/bitcoin",
                sentimentUpVotesPercentage = dev.ohoussein.crypto.presentation.model.LabelValue(64.0, "64 %"),
                sentimentDownVotesPercentage = dev.ohoussein.crypto.presentation.model.LabelValue(37.0, "37 %"),
                description = "<b>Lorem ipsum dolor sit amet</b>, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        )
    }

    val previewListCrypto by lazy {
        (0..20).map { previewCrypto }
    }
}
