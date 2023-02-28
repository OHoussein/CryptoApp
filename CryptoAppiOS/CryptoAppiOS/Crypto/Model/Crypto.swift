import Foundation

struct BaseCrypto: Hashable {
    let id: String
    let name: String
    let symbol: String
    let imageUrl: String
}

struct Crypto: Hashable {
    let base: BaseCrypto
    let price: CryptoPrice
    let priceChangePercentIn24h: LabelValue<Double>?
}

struct CryptoPrice: Hashable {
    let labelValue: LabelValue<Double>
}

struct LabelValue<V>: Hashable where V: Hashable {
    let value: V
    let label: String
}

struct CryptoDetails: Hashable {
    let base: BaseCrypto
    let hashingAlgorithm: String?
    let homePageUrl: String?
    let blockchainSite: String?
    let mainRepoUrl: String?
    let sentimentUpVotesPercentage: LabelValue<Double>?
    let sentimentDownVotesPercentage: LabelValue<Double>?
    let description: String
}
