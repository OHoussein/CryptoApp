@testable import CyptoAppiOS
import sharedModules
import XCTest

final class CryptoModelMapperTest: XCTestCase {
    func testConvertCryptoCryptoListModel() throws {
        let mapper = CryptoModelMapper()
        let domainCrypto = CryptoDomainCryptoModel(
            id: "bitcoin",
            name: "Bitcoin",
            imageUrl: "https://img.com/bitcoin",
            price: 16000.52,
            symbol: "BTC",
            priceChangePercentIn24h: 2.17,
            order: 1
        )

        let uiModelArray = mapper.convert(domain: CryptoDomainCryptoListModel(list: [domainCrypto]))

        let firstCrypto = uiModelArray.first!
        XCTAssertEqual("bitcoin", firstCrypto.base.id)
        XCTAssertEqual("Bitcoin", firstCrypto.base.name)
        XCTAssertEqual("https://img.com/bitcoin", firstCrypto.base.imageUrl)
        XCTAssertEqual("BTC", firstCrypto.base.symbol)
        XCTAssertEqual("$16,000.52", firstCrypto.price.labelValue.label)
        XCTAssertEqual(16000.52, firstCrypto.price.labelValue.value)
        XCTAssertEqual("2.2%", firstCrypto.priceChangePercentIn24h?.label)
        XCTAssertEqual(2.17, firstCrypto.priceChangePercentIn24h?.value)
    }

    func testConvertCryptoDomainCryptoDetailsModel() throws {
        let mapper = CryptoModelMapper()
        let domainCryptoDetails = CryptoDomainCryptoDetailsModel(
            id: "bitcoin",
            name: "Bitcoin",
            symbol: "BTC",
            imageUrl: "https://img.com/bitcoin",
            hashingAlgorithm: "SHA-256",
            homePageUrl: "https://bitcoin.com",
            blockchainSite: "https://btc.com",
            mainRepoUrl: "https://github.com/bitcoin",
            sentimentUpVotesPercentage: 64,
            sentimentDownVotesPercentage: 36,
            description: "bitcoin description"
        )

        let uiModel = mapper.convert(domain: domainCryptoDetails)

        XCTAssertEqual("bitcoin", uiModel.base.id)
        XCTAssertEqual("Bitcoin", uiModel.base.name)
        XCTAssertEqual("https://img.com/bitcoin", uiModel.base.imageUrl)
        XCTAssertEqual("BTC", uiModel.base.symbol)
        XCTAssertEqual("SHA-256", uiModel.hashingAlgorithm)
        XCTAssertEqual("https://bitcoin.com", uiModel.homePageUrl)
        XCTAssertEqual("https://btc.com", uiModel.blockchainSite)
        XCTAssertEqual("https://github.com/bitcoin", uiModel.mainRepoUrl)
        XCTAssertEqual("64%", uiModel.sentimentUpVotesPercentage?.label)
        XCTAssertEqual(64, uiModel.sentimentUpVotesPercentage?.value)
        XCTAssertEqual("36%", uiModel.sentimentDownVotesPercentage?.label)
        XCTAssertEqual(36, uiModel.sentimentDownVotesPercentage?.value)
        XCTAssertEqual("bitcoin description", uiModel.description)
    }
}
