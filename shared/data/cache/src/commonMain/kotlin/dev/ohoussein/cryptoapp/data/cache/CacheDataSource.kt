package dev.ohoussein.cryptoapp.data.cache

import kotlin.time.Duration

interface CacheDataSource<Key : Any, Data : Any> {

    suspend fun read(key: Key, ttl: Duration? = null): Data?

    suspend fun write(key: Key, data: Data?)

    suspend fun clearAll()
}
