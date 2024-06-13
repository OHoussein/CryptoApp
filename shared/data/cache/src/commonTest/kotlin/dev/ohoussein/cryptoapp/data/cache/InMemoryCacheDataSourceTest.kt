package dev.ohoussein.cryptoapp.data.cache

import dev.ohoussein.cryptoapp.data.cache.utils.FakeTimeSource
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.seconds

class InMemoryCacheDataSourceTest {

    private lateinit var cache: InMemoryCacheDataSource<String, String>
    private lateinit var fetcher: (String) -> String
    private lateinit var testTimeSource: FakeTimeSource

    @BeforeTest
    fun setUp() {
        fetcher = { key -> "fetched_$key" }
        testTimeSource = FakeTimeSource()

        cache = InMemoryCacheDataSource(fetcher, testTimeSource)
    }

    @Test
    fun `Given a cached value When read before expiry Then it should return the cached value`() = runTest {
        // Given
        cache.write("key1", "value1")
        // When
        val result = cache.read("key1")
        // Then
        assertEquals("value1", result)
    }

    @Test
    fun `Given a cached value When read after the expiry Then it should return the fetched value`() = runTest {
        // Given
        cache.write("key1", "value1")
        testTimeSource += 5.seconds
        // When
        val resultAfterExpiry = cache.read("key1", 2.seconds)
        // Then
        assertEquals("fetched_key1", resultAfterExpiry)
    }

    @Test
    fun `Given a cached values When clearAll and read Then it should return the fetched value`() = runTest {
        // Given
        cache.write("key1", "value1")
        cache.write("key2", "value2")
        // When
        cache.clearAll()
        val result = cache.read("key1", 2.seconds)
        // Then
        assertEquals("fetched_key1", result)
    }
}
