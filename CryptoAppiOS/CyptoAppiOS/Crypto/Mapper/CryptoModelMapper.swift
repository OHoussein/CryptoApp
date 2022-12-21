import Foundation
import sharedModules

class CryptoModelMapper {
    let locale: CryptoDomainLocale
    let priceFormatter: FormatterPriceFormatter
    let percentFormatter: FormatterPercentFormatter

    init(
        locale: CryptoDomainLocale,
        priceFormatter: FormatterPriceFormatter,
        percentFormatter: FormatterPercentFormatter
    ) {
        self.locale = locale
        self.priceFormatter = priceFormatter
        self.percentFormatter = percentFormatter
    }

    convenience init() {
        self.init(
            locale: SharedModulesKt.getLocale,
            priceFormatter: SharedModulesKt.getPriceFormatter,
            percentFormatter: SharedModulesKt.getPercentFormatter
        )
    }

    func convert(domain: CryptoDomainCryptoList) -> [Crypto] {
        return domain.list.map {
            convert(domain: $0)
        }
    }

    func convert(domain: CryptoDomainDomainCrypto) -> Crypto {
        var priceChangePercentIn24h: LabelValue<Double>?
        if let priceChangePercentIn24hDouble = domain.priceChangePercentIn24h?.doubleValue {
            let label = percentFormatter.invoke(percent: priceChangePercentIn24hDouble / 100)
            priceChangePercentIn24h = LabelValue(value: priceChangePercentIn24hDouble, label: label)
        }

        let priceLabel = priceFormatter.invoke(price: domain.price, currencyCode: locale.currencyCode)
        let cryptoPrice = CryptoPrice(labelValue: LabelValue(value: domain.price,
                                                             label: priceLabel))

        return Crypto(base: BaseCrypto(id: domain.id,
                                       name: domain.name,
                                       symbol: domain.symbol.uppercased(),
                                       imageUrl: domain.imageUrl),
                      price: cryptoPrice,
                      priceChangePercentIn24h: priceChangePercentIn24h)
    }

    func convert(domain: CryptoDomainDomainCryptoDetails) -> CryptoDetails {
        var sentimentUpVotesPercentage: LabelValue<Double>?
        if let sentimentUpVotesPercentageDouble = domain.sentimentUpVotesPercentage?.doubleValue {
            let label = percentFormatter.invoke(percent: sentimentUpVotesPercentageDouble / 100)
            sentimentUpVotesPercentage = LabelValue(value: sentimentUpVotesPercentageDouble,
                                                    label: label)
        }

        var sentimentDownVotesPercentage: LabelValue<Double>?
        if let sentimentDownVotesPercentageDouble = domain.sentimentDownVotesPercentage?.doubleValue {
            let label = percentFormatter.invoke(percent: sentimentDownVotesPercentageDouble / 100)
            sentimentDownVotesPercentage = LabelValue(value: sentimentDownVotesPercentageDouble,
                                                      label: label)
        }

        return CryptoDetails(base: BaseCrypto(id: domain.id,
                                              name: domain.name,
                                              symbol: domain.symbol.uppercased(),
                                              imageUrl: domain.imageUrl),
                             hashingAlgorithm: domain.hashingAlgorithm,
                             homePageUrl: domain.homePageUrl,
                             blockchainSite: domain.blockchainSite,
                             mainRepoUrl: domain.mainRepoUrl,
                             sentimentUpVotesPercentage: sentimentUpVotesPercentage,
                             sentimentDownVotesPercentage: sentimentDownVotesPercentage,
                             description: domain.description_.deleteHTMLTag())
    }
}
