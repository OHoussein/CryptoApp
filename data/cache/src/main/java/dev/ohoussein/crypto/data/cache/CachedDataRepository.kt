package dev.ohoussein.cryptoapp.data.cache

import kotlinx.coroutines.flow.Flow

class CachedDataRepository<Key : Any, Data : Any>(
    private val updater: suspend (Key) -> Data,
    private val cacheStreamer: (Key) -> Flow<Data>,
    private val cacheWriter: suspend (Key, Data) -> Unit,
) {

    fun stream(key: Key): Flow<Data> = cacheStreamer(key)

    suspend fun refresh(key: Key) {
        val newData = updater(key)
        cacheWriter(key, newData)
    }
}
