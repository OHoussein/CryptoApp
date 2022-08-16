package dev.ohoussein.cryptoapp.data.cache

import dev.ohoussein.crypto.data.cache.DataFetcher
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.withIndex

class CachedDataRepositoryImpl<Key : Any, Value : Any>(
    dataFetcher: DataFetcher<Key, Value>,
    cache: Cache<Key, Value>,
) : CachedDataRepository<Key, Value>(dataFetcher, cache) {

    override fun stream(key: Key, cachePolicy: CachePolicy): Flow<CachedData<Value>> {
        return when (cachePolicy) {
            CachePolicy.FRESH -> onlyFreshData(key)
            CachePolicy.CACHE -> fromCacheThenNetworkIfAbsent(key)
            CachePolicy.CACHE_THEN_FRESH -> fromCacheThenUpdate(key)
        }
    }

    private fun onlyFreshData(key: Key): Flow<CachedData<Value>> = flow {
        val data = dataFetcher.fetch(key)
        emit(CachedData.fresh(data))
        cache.write(key, data)
    }

    private fun fromCacheThenNetworkIfAbsent(key: Key): Flow<CachedData<Value>> = cache
        .reader(key)
        .withIndex()
        .onEach {
            if (it.index == 0 && it.value == null)
                onlyFreshData(key)
                    .collect()
        }
        .map { data ->
            data.value?.let { CachedData.cached(it, false) }
        }
        .filterNotNull()

    private fun fromCacheThenUpdate(key: Key): Flow<CachedData<Value>> =
        merge(
            cache
                .reader(key)
                .withIndex()
                .map { data ->
                    data.value?.let { CachedData.cached(it, data.index == 0) }
                }
                .filterNotNull(),
            onlyFreshData(key)
                .dropWhile { true }
        )
}
