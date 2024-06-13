package dev.ohoussein.cryptoapp.data.cache

import kotlin.time.Duration
import kotlin.time.TimeSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCacheDataSource<Key : Any, Data : Any>(
    private val fetcher: suspend (Key) -> Data,
    private val timeSource: TimeSource = TimeSource.Monotonic,
) : CacheDataSource<Key, Data> {

    private data class CacheEntry<Data>(
        val data: Data,
        val writeTimeMs: Long,
    )

    private val cache = mutableMapOf<Key, CacheEntry<Data>>()
    private val keyLocks = mutableMapOf<Key, Mutex>()
    private val cacheLock = Mutex()

    private val now
        get() = timeSource.markNow().elapsedNow()

    override suspend fun read(key: Key, ttl: Duration?): Data {
        getKeyMutex(key).withLock {
            val entry = cache[key] ?: return fetchAndWriteToCache(key)
            if (ttl != null && now.inWholeMilliseconds - entry.writeTimeMs > ttl.inWholeMilliseconds) {
                cache.remove(key)
                return fetchAndWriteToCache(key)
            }
            return entry.data
        }
    }

    override suspend fun write(key: Key, data: Data?) {
        getKeyMutex(key).withLock {
            if (data == null) {
                cache.remove(key)
            } else {
                val writeTime = now.inWholeMilliseconds
                println("new CacheEntry = ${CacheEntry(data, writeTime)}")
                cache[key] = CacheEntry(data, writeTime)
            }
        }
    }

    private suspend fun fetchAndWriteToCache(key: Key): Data {
        val data = fetcher(key)
        write(key, data)
        return data
    }

    override suspend fun clearAll() {
        cache.clear()
    }

    private suspend fun getKeyMutex(key: Key): Mutex {
        return cacheLock.withLock {
            keyLocks.getOrPut(key) { Mutex() }
        }
    }
}
