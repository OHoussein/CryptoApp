package dev.ohoussein.cryptoapp.crypto.data.mapper

import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.data.network.crypto.model.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ApiDomainModelMapperTest {

    private lateinit var mapper: ApiDomainModelMapper

    @BeforeTest
    fun setUp() {
        mapper = ApiDomainModelMapper(Locale("USD", "en"))
    }

    @Test
    fun `Given a list of TopCryptoResponse When convert It should return the right domain model`() {
        val sparkline = SparkLineDTO(listOf(1.0, 2.0, 3.0))
        val topCryptoResponseList = listOf(
            TopCryptoResponse("1", "BTC", "Bitcoin", "url1", 50000.0, 5.0, sparkline),
            TopCryptoResponse("2", "ETH", "Ethereum", "url2", 2000.0, 10.0, null)
        )

        val result = mapper.convert(topCryptoResponseList)

        assertEquals(2, result.size)

        // Validate the first item
        val firstItem = result[0]
        assertEquals("1", firstItem.id)
        assertEquals("BTC", firstItem.symbol)
        assertEquals("Bitcoin", firstItem.name)
        assertEquals("url1", firstItem.imageUrl)
        assertEquals(50000.0, firstItem.price, 0.0)
        assertEquals(5.0, firstItem.priceChangePercentIn24h!!, 0.0)
        assertEquals(0, firstItem.order)
        assertEquals(listOf(1.0, 2.0, 3.0), firstItem.sparkLine7d)

        // Validate the second item
        val secondItem = result[1]
        assertEquals("2", secondItem.id)
        assertEquals("ETH", secondItem.symbol)
        assertEquals("Ethereum", secondItem.name)
        assertEquals("url2", secondItem.imageUrl)
        assertEquals(2000.0, secondItem.price, 0.0)
        assertEquals(10.0, secondItem.priceChangePercentIn24h!!, 0.0)
        assertEquals(1, secondItem.order)
        assertNull(secondItem.sparkLine7d)
    }

    @Test
    fun `Given a list of CryptoDetailsResponse When convert It should return the right domain model`() {
        val image = CryptoImageResponse("thumb_url", "small_url", "large_url")
        val links = CryptoLinksResponse(
            listOf("homepageUrl"),
            listOf("blockchainSiteUrl"),
            mapOf("repoKey" to listOf("repoUrl")),
        )
        val description = mapOf("en" to "English description")
        val cryptoDetailsResponse = CryptoDetailsResponse(
            id = "1",
            symbol = "BTC",
            name = "Bitcoin",
            hashingAlgorithm = "SHA-256",
            description = description,
            sentimentUpVotesPercentage = 80.0,
            sentimentDownVotesPercentage = 20.0,
            image = image,
            links = links,
        )

        val result = mapper.convert(cryptoDetailsResponse)

        assertEquals("1", result.id)
        assertEquals("BTC", result.symbol)
        assertEquals("Bitcoin", result.name)
        assertEquals("large_url", result.imageUrl)
        assertEquals("SHA-256", result.hashingAlgorithm)
        assertEquals("homepageUrl", result.homePageUrl)
        assertEquals("blockchainSiteUrl", result.blockchainSite)
        assertEquals("repoUrl", result.mainRepoUrl)
        assertEquals(80.0, result.sentimentUpVotesPercentage!!, 0.0)
        assertEquals(20.0, result.sentimentDownVotesPercentage!!, 0.0)
        assertEquals("English description", result.description)
    }

    @Test
    fun `Given a HistoricalPricesDTO When convert It should return the right domain model`() {
        val dto = HistoricalPricesDTO(
            prices = listOf(
                listOf(1711843200000.0, 69702.3087473573),
                listOf(1711929600000.0, 71246.95144060145),
                listOf(1711983682000.0, 68887.74951585678)
            )
        )
        val expectedDomain: List<HistoricalPrice> = listOf(
            HistoricalPrice(1711843200000, 69702.3087473573),
            HistoricalPrice(1711929600000, 71246.95144060145),
            HistoricalPrice(1711983682000, 68887.74951585678),
        )

        val result = mapper.convert(dto)

        assertEquals(expectedDomain, result)
    }
}
