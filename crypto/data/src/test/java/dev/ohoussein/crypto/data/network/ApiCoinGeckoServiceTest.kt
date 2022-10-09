package dev.ohoussein.crypto.data.network

import dev.ohoussein.core.test.network.NetworkUtils.readMockFile
import dev.ohoussein.core.test.network.NetworkUtils.withResponse
import dev.ohoussein.crypto.data.api.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.data.network.NetworkBuilder.createRetrofit
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class ApiCoinGeckoServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: ApiCoinGeckoService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        service = ApiCoinGeckoService.create(createRetrofit(baseUrl = mockWebServer.url("/")))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun apiGetTopCryptoTest() = runBlocking {
        mockWebServer.withResponse(
            MockResponse()
                .setResponseCode(200)
                .setBody(readMockFile("mock_top_crypto_list.json"))
        )

        val response = service.getTopCrypto("")
        assertNotNull(response)
        assertEquals(100, response.size)

        val firstItem = response.first()

        assertEquals("bitcoin", firstItem.id)
        assertEquals("Bitcoin", firstItem.name)
        assertEquals(31827.0, firstItem.currentPrice, 0.0001)
        assertEquals("btc", firstItem.symbol)
    }

    @Test
    fun apiGetCryptoDetails() = runBlocking {
        mockWebServer.withResponse(
            MockResponse()
                .setResponseCode(200)
                .setBody(readMockFile("mock_crypto_details.json"))
        )
        val details = service.getCryptoDetails("bitcoin")
        assertNotNull(details)

        assertEquals("bitcoin", details.id)
        assertEquals("Bitcoin", details.name)
        assertEquals("btc", details.symbol)
        assertEquals(
            "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            details.image.large
        )
        assertEquals(32.67, details.sentimentDownVotesPercentage, 0.001)
        assertEquals(67.33, details.sentimentUpVotesPercentage, 0.001)
        assertEquals("SHA-256", details.hashingAlgorithm)
        assertTrue(details.links.blockchainSite.isNotEmpty())
        assertTrue(details.links.homepage.isNotEmpty())
        assertTrue(details.links.reposUrl.isNotEmpty())
    }
}
