// swiftlint:disable all

import Foundation

struct CryptoDataMock {
    static func mockedCryptoList() -> [Crypto] {
        return [
            Crypto(
                base: BaseCrypto(
                    id: "bitcoin",
                    name: "Bitcoin",
                    symbol: "BTC",
                    imageUrl: "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 21724.0, label: "$21,724.00")),
                priceChangePercentIn24h: LabelValue(value: 0.99867, label: "1.00%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "ethereum",
                    name: "Ethereum",
                    symbol: "ETH",
                    imageUrl: "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 1711.9, label: "$1,711.90")),
                priceChangePercentIn24h: LabelValue(value: 3.31843, label: "3.32%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "tether",
                    name: "Tether",
                    symbol: "USDT",
                    imageUrl: "https://assets.coingecko.com/coins/images/325/large/Tether-logo.png?1598003707"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 1.0, label: "$1.00")),
                priceChangePercentIn24h: LabelValue(value: -0.12258, label: "-0.12 %")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "usd-coin",
                    name: "USD Coin",
                    symbol: "USDC",
                    imageUrl: "https://assets.coingecko.com/coins/images/6319/large/USD_Coin_icon.png?1547042389"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 0.999751, label: "$1.00")),
                priceChangePercentIn24h: LabelValue(value: -0.18671, label: "-0.19%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "binancecoin",
                    name: "BNB",
                    symbol: "BNB",
                    imageUrl: "https://assets.coingecko.com/coins/images/825/large/bnb-icon2_2x.png?1644979850"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 303.95, label: "$303.95")),
                priceChangePercentIn24h: LabelValue(value: 2.08925, label: "2.09 %")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "binance-usd",
                    name: "Binance USD",
                    symbol: "BUSD",
                    imageUrl: "https://assets.coingecko.com/coins/images/9576/large/BUSD.png?1568947766"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 1.001, label: "$1.00")),
                priceChangePercentIn24h: LabelValue(value: -0.07009, label: "-0.07%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "ripple",
                    name: "XRP",
                    symbol: "XRP",
                    imageUrl: "https://assets.coingecko.com/coins/images/44/large/xrp-symbol-white-128.png?1605778731"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 0.349139, label: "$0.35")),
                priceChangePercentIn24h: LabelValue(value: 1.02258, label: "1.02%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "cardano",
                    name: "Cardano",
                    symbol: "ADA",
                    imageUrl: "https://assets.coingecko.com/coins/images/975/large/cardano.png?1547034860"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 0.46538, label: "$0.47")),
                priceChangePercentIn24h: LabelValue(value: 0.56511, label: "0.57%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "solana",
                    name: "Solana",
                    symbol: "SOL",
                    imageUrl: "https://assets.coingecko.com/coins/images/4128/large/solana.png?1640133422"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 34.6, label: "$34.60")),
                priceChangePercentIn24h: LabelValue(value: -4.03468, label: "-4.03%")
            ),
            Crypto(
                base: BaseCrypto(
                    id: "dogecoin",
                    name: "Dogecoin",
                    symbol: "DOGE",
                    imageUrl: "https://assets.coingecko.com/coins/images/5/large/dogecoin.png?1547792256"
                ),
                price: CryptoPrice(labelValue: LabelValue(value: 0.068203, label: "$0.07")),
                priceChangePercentIn24h: LabelValue(value: -3.44207, label: "-3.44 %")
            ),
        ]
    }

    static func mockedCryptoDetails() -> CryptoDetails {
        CryptoDetails(
            base: BaseCrypto(
                id: "bitcoin",
                name: "Bitcoin",
                symbol: "BTC",
                imageUrl: "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
            ),
            hashingAlgorithm: "SHA-256",
            homePageUrl: "http://www.bitcoin.org",
            blockchainSite: "https://btc.com/",
            mainRepoUrl: "https://github.com/bitcoin/bitcoin",
            sentimentUpVotesPercentage: LabelValue(
                value: 64.0,
                label: "64 %"
            ),
            sentimentDownVotesPercentage: LabelValue(
                value: 37.0,
                label: "37 %"
            ),
            description: "Bitcoin is the first and most widely recognized cryptocurrency. It enables peer-to-peer exchange of value in the digital realm through the use of a decentralized protocol, cryptography, and a mechanism to achieve global consensus on the state of a periodically updated public transaction ledger called a 'blockchain.'\nPractically speaking, Bitcoin is a form of digital money that (1) exists independently of any government, state, or financial institution, (2) can be transferred globally without the need for a centralized intermediary, and (3) has a known monetary policy that arguably cannot be altered."
        )
    }
}
