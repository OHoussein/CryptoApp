package dev.ohoussein.cryptoapp.data.cache

import dev.ohoussein.crypto.data.cache.DataFetcher
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import kotlinx.coroutines.flow.Flow

abstract class CachedDataRepository<Key : Any, Output : Any>(
    protected val dataFetcher: DataFetcher<Key, Output>,
    protected val cache: Cache<Key, Output>,
) {

    abstract fun stream(key: Key, cachePolicy: CachePolicy): Flow<CachedData<Output>>

    companion object {
        fun <Key : Any, Value : Any> create(
            dataFetcher: DataFetcher<Key, Value>,
            cache: Cache<Key, Value>,
        ): CachedDataRepository<Key, Value> = CachedDataRepositoryImpl(dataFetcher, cache)
    }
}
