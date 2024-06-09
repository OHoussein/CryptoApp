@file:Suppress("MagicNumber", "MaxLineLength")

package dev.ohoussein.cryptoapp.crypto.presentation.fake

import dev.ohoussein.cryptoapp.crypto.presentation.model.*

object Fake {
    val previewCrypto by lazy {
        Crypto(
            info = CryptoInfo(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            ),
            price = CryptoPrice(
                LabelValue(31827.0, "$31 827")
            ),
            priceChangePercentIn24h = LabelValue(
                1.13,
                "1.13%"
            ),
            sparkline7d = null,
        )
    }

    val previewCryptoDetails by lazy {
        CryptoDetails(
            base = CryptoInfo(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            ),
            hashingAlgorithm = "SHA-256",
            homePageUrl = "http://www.bitcoin.org",
            blockchainSite = "https://btc.com/",
            mainRepoUrl = "https://github.com/bitcoin/bitcoin",
            sentimentUpVotesPercentage = LabelValue(
                64.0,
                "64 %"
            ),
            sentimentDownVotesPercentage = LabelValue(
                37.0,
                "37 %"
            ),
            description = "<b>Lorem ipsum dolor sit amet</b>, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
        )
    }

    val previewListCrypto = listOf(
        Crypto(
            info = CryptoInfo(
                id = "bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                imageUrl = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 21724.0, label = "$21,724.00")),
            priceChangePercentIn24h = LabelValue(value = 0.99867, label = "1.00%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "ethereum",
                name = "Ethereum",
                symbol = "ETH",
                imageUrl = "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 1711.9, label = "$1,711.90")),
            priceChangePercentIn24h = LabelValue(value = 3.31843, label = "3.32%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "tether",
                name = "Tether",
                symbol = "USDT",
                imageUrl = "https://assets.coingecko.com/coins/images/325/large/Tether-logo.png?1598003707",
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 1.0, label = "$1.00")),
            priceChangePercentIn24h = LabelValue(value = -0.12258, label = "-0.12 %"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "usd-coin",
                name = "USD Coin",
                symbol = "USDC",
                imageUrl = "https://assets.coingecko.com/coins/images/6319/large/USD_Coin_icon.png?1547042389",
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 0.999751, label = "$1.00")),
            priceChangePercentIn24h = LabelValue(value = -0.18671, label = "-0.19%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "binancecoin",
                name = "BNB",
                symbol = "BNB",
                imageUrl = "https://assets.coingecko.com/coins/images/825/large/bnb-icon2_2x.png?1644979850"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 303.95, label = "$303.95")),
            priceChangePercentIn24h = LabelValue(value = 2.08925, label = "2.09 %"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "binance-usd",
                name = "Binance USD",
                symbol = "BUSD",
                imageUrl = "https://assets.coingecko.com/coins/images/9576/large/BUSD.png?1568947766"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 1.001, label = "$1.00")),
            priceChangePercentIn24h = LabelValue(value = -0.07009, label = "-0.07%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "ripple",
                name = "XRP",
                symbol = "XRP",
                imageUrl = "https://assets.coingecko.com/coins/images/44/large/xrp-symbol-white-128.png?1605778731"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 0.349139, label = "$0.35")),
            priceChangePercentIn24h = LabelValue(value = 1.02258, label = "1.02%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "cardano",
                name = "Cardano",
                symbol = "ADA",
                imageUrl = "https://assets.coingecko.com/coins/images/975/large/cardano.png?1547034860"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 0.46538, label = "$0.47")),
            priceChangePercentIn24h = LabelValue(value = 0.56511, label = "0.57%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "solana",
                name = "Solana",
                symbol = "SOL",
                imageUrl = "https://assets.coingecko.com/coins/images/4128/large/solana.png?1640133422"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 34.6, label = "$34.60")),
            priceChangePercentIn24h = LabelValue(value = -4.03468, label = "-4.03%"),
            sparkline7d = null,
        ),
        Crypto(
            info = CryptoInfo(
                id = "dogecoin",
                name = "Dogecoin",
                symbol = "DOGE",
                imageUrl = "https://assets.coingecko.com/coins/images/5/large/dogecoin.png?1547792256"
            ),
            price = CryptoPrice(labelValue = LabelValue(value = 0.068203, label = "$0.07")),
            priceChangePercentIn24h = LabelValue(value = -3.44207, label = "-3.44 %"),
            sparkline7d = null,
        ),
    )
}
