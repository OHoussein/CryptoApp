package dev.ohoussein.cryptoapp.data.cache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

class CachedDataRepositoryImplTest {

    @Test
    fun stream(): Unit = runBlocking {
        val data = listOf("data1", "data2")
        val cacheStreamerParamCalls = mutableListOf<String>()
        val cachedDataRepository = CachedDataRepository<String, List<String>>(
            updater = { listOf() },
            cacheStreamer = { key ->
                cacheStreamerParamCalls.add(key)
                flowOf(data)
            },
            cacheWriter = { _, _ -> },
        )

        val steamedData = cachedDataRepository.stream("key")

        assertEquals(data, steamedData.first())
        assertEquals(listOf("key"), cacheStreamerParamCalls)
    }

    @Test
    fun refresh(): Unit = runBlocking {
        val data = listOf("data1", "data2")
        val updaterParamCalls = mutableListOf<String>()
        val cacheWriterParamCalls = mutableListOf<Pair<String, List<String>>>()

        val cachedDataRepository = CachedDataRepository<String, List<String>>(
            updater = {
                updaterParamCalls.add(it)
                data
            },
            cacheStreamer = { flowOf(data) },
            cacheWriter = { key, value ->
                cacheWriterParamCalls.add(key to value)
            },
        )

        cachedDataRepository.refresh("key")

        assertEquals(listOf("key"), updaterParamCalls)
        assertEquals(listOf("key" to data), cacheWriterParamCalls)
    }
}
