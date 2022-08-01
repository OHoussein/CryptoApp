package dev.ohoussein.crypto.data.network

import dev.ohoussein.core.test.network.NetworkUtils.readMockFile
import dev.ohoussein.core.test.network.NetworkUtils.withResponse
import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.model.CryptoImageResponse
import dev.ohoussein.cryptoapp.data.network.builder.NetworkBuilder.createRetrofit
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class ApiCryptoServiceTest : BehaviorSpec({

    coroutineTestScope = true

    val mockWebServer = MockWebServer()
    val service by lazy { ApiCryptoService.create(createRetrofit(baseUrl = mockWebServer.url("/"))) }

    given("a success getTopCrypto response") {
        mockWebServer.withResponse(
            MockResponse()
                .setResponseCode(200)
                .setBody(readMockFile("mock_top_crypto_list.json"))
        )

        `when`("getTopCrypto") {
            val response = service.getTopCrypto("")
            val firstItem = response.first()

            then("it should get a success response") {
                firstItem.id shouldBe "bitcoin"
                firstItem.name shouldBe "Bitcoin"
                firstItem.currentPrice shouldBe 31827.0
                firstItem.symbol shouldBe "btc"
            }
        }
    }

    given("a success getCryptoDetails response") {
        mockWebServer.withResponse(
            MockResponse()
                .setResponseCode(200)
                .setBody(readMockFile("mock_crypto_details.json"))
        )

        `when`("getCryptoDetails") {
            val details = service.getCryptoDetails("bitcoin")

            then("it should get a success response") {
                details shouldNotBe null
                details.id shouldBe "bitcoin"
                details.name shouldBe "Bitcoin"
                details.symbol shouldBe "btc"
                details.image shouldBe CryptoImageResponse(
                    thumb = "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png?1547033579",
                    small = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1547033579",
                    large = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                )
                details.sentimentDownVotesPercentage shouldBe 32.67
                details.sentimentUpVotesPercentage shouldBe 67.33
                details.hashingAlgorithm shouldBe "SHA-256"
                details.links.blockchainSite shouldContain "https://blockchair.com/bitcoin/"
                details.links.homepage shouldContain "http://www.bitcoin.org"
                details.links.reposUrl shouldContainKey "github"
                details.links.reposUrl["github"]!! shouldContain "https://github.com/bitcoin/bitcoin"
            }
        }
    }
})
