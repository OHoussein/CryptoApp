package dev.ohoussein.crypto.presentation.mapper

import dev.ohoussein.crypto.presentation.model.BaseCrypto
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.model.CryptoPrice
import dev.ohoussein.crypto.presentation.model.LabelValue
import dev.ohoussein.cryptoapp.core.formatter.PercentFormatter
import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.crypto.domain.model.defaultLocale
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DomainModelMapperTest : BehaviorSpec({

    val currency = "USD"
    val priceFormatter = mock<PriceFormatter> {
        whenever(mock.invoke(30_000.00, currency)).thenReturn("30 000$")
    }
    val percentFormatter = mock<PercentFormatter> {
        whenever(mock.invoke(0.1)).thenReturn("10%")
        whenever(mock.invoke(70.0)).thenReturn("70%")
        whenever(mock.invoke(30.0)).thenReturn("30%")
    }
    val domainModelMapper = DomainModelMapper(
        priceFormatter = priceFormatter,
        percentFormatter = percentFormatter,
        locale = defaultLocale,
    )

    given("a List<DomainCrypto>") {

        val crypto = CryptoList(
            listOf(
                DomainCrypto(
                    id = "1",
                    name = "Bitcoin",
                    imageUrl = "https://bitcoin.com",
                    symbol = "BTC",
                    price = 30_000.00,
                    priceChangePercentIn24h = 10.0,
                    order = 1,
                )
            )
        )

        `when`("convert") {
            val uiModel = domainModelMapper.convert(crypto)

            then("it should convert to the right data") {
                uiModel.first() shouldBe Crypto(
                    base = BaseCrypto(
                        id = "1",
                        symbol = "BTC",
                        name = "Bitcoin",
                        imageUrl = "https://bitcoin.com",
                    ),
                    price = CryptoPrice(labelValue = LabelValue(30_000.00, "30 000$")),
                    priceChangePercentIn24h = LabelValue(10.0, "10%")
                )
            }
        }
    }

    given("a DomainCryptoDetails") {

        val crypto = DomainCryptoDetails(
            id = "1",
            name = "Bitcoin",
            imageUrl = "https://bitcoin.com",
            symbol = "BTC",
            hashingAlgorithm = "SHA-256",
            homePageUrl = "Http://home.com",
            mainRepoUrl = "Http://repo.com",
            blockchainSite = "Http://blockchain.com",
            sentimentUpVotesPercentage = 70.0,
            sentimentDownVotesPercentage = 30.0,
            description = "Bitcoin description",
        )

        `when`("convert") {
            val uiModel = domainModelMapper.convert(crypto)

            then("it should convert to the right data") {
                uiModel shouldBe CryptoDetails(
                    base = BaseCrypto(
                        id = "1",
                        symbol = "BTC",
                        name = "Bitcoin",
                        imageUrl = "https://bitcoin.com",
                    ),
                    hashingAlgorithm = "SHA-256",
                    homePageUrl = "Http://home.com",
                    mainRepoUrl = "Http://repo.com",
                    blockchainSite = "Http://blockchain.com",
                    sentimentUpVotesPercentage = LabelValue(70.0, "70%"),
                    sentimentDownVotesPercentage = LabelValue(30.0, "30%"),
                    description = "Bitcoin description",
                )
            }
        }
    }
})
