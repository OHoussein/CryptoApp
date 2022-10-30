package dev.ohoussein.cryptoapp.data.cache

import app.cash.turbine.test
import dev.ohoussein.crypto.data.cache.DataFetcher
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.cacheddata.DataOrigin
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.io.IOException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CachedDataRepositoryImplTest : DescribeSpec({

    describe("a real cache and fetcher") {
        val cache = TestCache()
        val fetcher: DataFetcher<String, List<String>> = mock()

        describe("a CachedDataRepository") {
            val cachedDataRepository: CachedDataRepository<String, List<String>> = CachedDataRepositoryImpl(
                fetcher,
                cache,
            )

            context("FRESH cachePolicy") {

                describe("a success response from the fetcher") {
                    val key = "key1"
                    val data = listOf("first", "second")
                    whenever(fetcher.fetch(key)).thenReturn(data)

                    describe("Stream with a FRESH cachePolicy") {
                        val stream = cachedDataRepository.stream(key, CachePolicy.FRESH)

                        it("should get the data directly from the DataFetcher and write it into the cache") {
                            TestScope().launch {
                                stream.test {
                                    awaitItem().apply {
                                        isLoading shouldBe false
                                        origin shouldBe DataOrigin.FRESH
                                        data shouldBe data
                                    }
                                    awaitComplete()
                                }
                                cache.read(key) shouldBe data
                            }
                        }
                    }
                }

                describe("an error response from the fetcher") {
                    val key = "key1"
                    whenever(fetcher.fetch(key)).thenAnswer { throw IOException() }

                    describe("Stream with a FRESH cachePolicy") {
                        val stream = cachedDataRepository.stream(key, CachePolicy.FRESH)

                        it("should get an error and not insert the data into the cache") {
                            TestScope().launch {
                                stream.test { awaitError() }
                                cache.read(key) shouldBe null
                            }
                        }
                    }
                }
            }

            context("CACHE cachePolicy") {

                describe("a cache with wanted data") {
                    val key = "key1"
                    val data = mutableListOf("first", "second")
                    cache.initialData(key to data)

                    describe("Stream with a CACHE cachePolicy") {
                        val stream = cachedDataRepository.stream(key, CachePolicy.CACHE)

                        it("should get the data directly from the cache ") {
                            TestScope().launch {
                                stream.test {
                                    awaitItem().apply {
                                        isLoading shouldBe false
                                        origin shouldBe DataOrigin.CACHE
                                        data shouldBe data
                                    }
                                }
                            }
                        }
                    }
                }

                context("a cache without the wanted data") {

                    describe("a success response from the fetcher") {
                        val key = "key2"
                        val data = listOf("first", "second")
                        whenever(fetcher.fetch(key)).thenReturn(data)

                        describe("Stream with a CACHE cachePolicy") {
                            val stream = cachedDataRepository.stream(key, CachePolicy.CACHE)

                            it("should get the data directly from the fetcher then save it in the cache") {
                                TestScope().launch {
                                    stream.test {
                                        awaitItem().apply {
                                            isLoading shouldBe false
                                            origin shouldBe DataOrigin.FRESH
                                            data shouldBe data
                                        }
                                        awaitComplete()
                                    }
                                    cache.read(key) shouldBe data
                                }
                            }
                        }
                    }

                    describe("an error response from the fetcher") {
                        val key = "key2"
                        whenever(fetcher.fetch(key)).thenAnswer { throw IOException() }

                        describe("Stream with a CACHE cachePolicy") {
                            val stream = cachedDataRepository.stream(key, CachePolicy.FRESH)

                            it("should an error and not insert the data into the cache") {
                                stream.test { awaitError() }
                                cache.read(key) shouldBe null
                            }
                        }
                    }
                }
            }

            context("CACHE_THEN_FRESH cachePolicy") {

                describe("a cache with wanted data") {
                    val key = "key3"
                    val data = mutableListOf("first", "second")
                    cache.initialData(key to data)

                    describe("a success response from the fetcher") {
                        val freshData = listOf("first", "second", "third")
                        whenever(fetcher.fetch(key)).thenReturn(freshData)

                        describe("Stream with a CACHE_THEN_FRESH cachePolicy") {
                            val stream = cachedDataRepository.stream(key, CachePolicy.CACHE_THEN_FRESH)

                            it("get the data from the cache then from the DataFetcher and update the cache") {
                                TestScope().launch {
                                    stream.test {
                                        awaitItem() shouldBe CachedData.cached(data, true)
                                        awaitItem() shouldBe CachedData.fresh(freshData)
                                        awaitComplete()
                                    }
                                    cache.read(key) shouldBe freshData
                                }
                            }
                        }
                    }

                    describe("an error response from the fetcher") {
                        whenever(fetcher.fetch(key)).thenAnswer {
                            throw IOException()
                        }

                        describe("Stream with a CACHE_THEN_FRESH cachePolicy") {
                            val stream = cachedDataRepository.stream(key, CachePolicy.CACHE_THEN_FRESH)

                            it("should get the data from the cache and then raise an error") {

                                val scope = TestScope()
                                scope.launch {
                                    stream
                                        .test {
                                            expectMostRecentItem() shouldBe CachedData.cached(data, true)
                                            awaitError()
                                        }
                                    cache.read(key) shouldBe data
                                }
                            }
                        }
                    }
                }

                describe("a cache without the wanted data") {
                    val key = "key5"

                    describe("a success response from the fetcher") {
                        val freshData = listOf("first", "second", "third")
                        whenever(fetcher.fetch(key)).thenReturn(freshData)

                        describe("Stream with a CACHE_THEN_FRESH cachePolicy") {
                            val stream = cachedDataRepository.stream(key, CachePolicy.CACHE_THEN_FRESH)

                            it("should get the data from the DataFetcher and write it to the cache") {
                                TestScope().launch {
                                    stream.test {
                                        awaitItem() shouldBe CachedData.fresh(freshData)
                                        awaitComplete()
                                    }
                                    cache.read(key) shouldBe freshData
                                }
                            }
                        }
                    }

                    describe("an error response from the fetcher") {
                        whenever(fetcher.fetch(key)).thenAnswer { throw IOException() }

                        describe("Stream with a CACHE_THEN_FRESH cachePolicy") {
                            val stream = cachedDataRepository.stream(key, CachePolicy.CACHE_THEN_FRESH)

                            it("should raise an error") {
                                TestScope().launch {
                                    stream.test {
                                        awaitError()
                                    }
                                    cache.read(key) shouldBe null
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    isolationMode = IsolationMode.InstancePerTest
})

private class TestCache : Cache<String, List<String>> {
    private val cacheData = mutableMapOf<String, List<String>>()
    private val mutableStateFlow: MutableStateFlow<Map<String, List<String>>> = MutableStateFlow(cacheData)

    override fun reader(key: String): Flow<List<String>?> {
        return mutableStateFlow.map {
            it[key]
        }
    }

    override suspend fun write(key: String, value: List<String>) {
        cacheData[key] = value
        mutableStateFlow.emit(cacheData)
    }

    fun read(key: String) = cacheData[key]

    fun initialData(vararg data: Pair<String, List<String>>) {
        cacheData.putAll(data)
        mutableStateFlow.value = cacheData
    }
}
