package dev.ohoussein.cryptoapp.data.network.crypto.service

import dev.ohoussein.cryptoapp.data.network.crypto.model.CryptoImageResponse
import dev.ohoussein.cryptoapp.data.network.crypto.model.TopCryptoResponse
import dev.ohoussein.cryptoapp.data.network.crypto.service.mocks.mockCryptoDetailsJson
import dev.ohoussein.cryptoapp.data.network.crypto.service.mocks.mockTopCryptoListJson
import dev.ohoussein.cryptoapp.data.network.crypto.service.utils.mockedHttpClient
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ApiCryptoServiceImplTest {

    @Test
    fun test_getTopCrypto(): Unit = runBlocking {
        val httpClient = mockedHttpClient(mockTopCryptoListJson)
        val apiCryptoService: ApiCryptoService = ApiCryptoServiceImpl(httpClient)

        val topCryptoList = apiCryptoService.getTopCrypto("USD")

        val expectedFirstItem = TopCryptoResponse(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
            currentPrice = 31827.0,
            priceChangePercentIn24h = -0.4901,
        )
        assertEquals(expectedFirstItem, topCryptoList.first())
    }

    @Test
    fun test_getCryptoDetails(): Unit = runBlocking {
        val httpClient = mockedHttpClient(mockCryptoDetailsJson)
        val apiCryptoService: ApiCryptoService = ApiCryptoServiceImpl(httpClient)

        val details = apiCryptoService.getCryptoDetails("bitcoin")

        assertNotNull(details)
        with(details) {
            assertEquals("bitcoin", id)
            assertEquals("Bitcoin", name)
            assertEquals("btc", symbol)
            assertEquals(32.67, sentimentDownVotesPercentage)
            assertEquals(67.33, sentimentUpVotesPercentage)
            assertEquals("SHA-256", hashingAlgorithm)
            assertContains(links.blockchainSite, "https://blockchair.com/bitcoin/")
            assertContains(links.homepage, "http://www.bitcoin.org")
            assertContains(links.reposUrl, "github")
            assertContains(links.reposUrl["github"]!!, "https://github.com/bitcoin/bitcoin")

            assertEquals(
                CryptoImageResponse(
                    thumb = "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png?1547033579",
                    small = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png?1547033579",
                    large = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
                ),
                image
            )
        }
    }
}
