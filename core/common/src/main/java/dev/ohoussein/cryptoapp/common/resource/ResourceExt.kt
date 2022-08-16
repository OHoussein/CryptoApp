package dev.ohoussein.cryptoapp.common.resource

import dev.ohoussein.cryptoapp.cacheddata.CachedData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

fun <T> Flow<T>.asResourceFlow(previousData: T? = null): Flow<Resource<T>> {
    var latestData: T? = previousData
    return this
        .map<T, Resource<T>> {
            latestData = it
            Resource.success(it)
        }
        .onStart {
            emit(Resource.loading(latestData))
        }
        .catch {
            Timber.e(it)
            emit(Resource.error(it, latestData))
        }
}

fun <T> Flow<CachedData<T>>.asCachedResourceFlow(previousData: T? = null): Flow<Resource<T>> {
    var latestData: T? = previousData
    return this
        .map {
            latestData = it.data
            if (it.isLoading)
                Resource.loading(it.data)
            else
                Resource.success(it.data)
        }
        .onStart {
            emit(Resource.loading(latestData))
        }
        .catch { error ->
            Timber.e(error)
            emit(Resource.error(error, latestData))
        }
}
