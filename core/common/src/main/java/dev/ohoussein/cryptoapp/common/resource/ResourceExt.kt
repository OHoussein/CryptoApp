package dev.ohoussein.cryptoapp.common.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import timber.log.Timber

fun <T> Flow<T>.asResourceFlow(previousData: T? = null): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> {
            Resource.success(it)
        }
        .onStart {
            emit(Resource.loading(previousData))
        }
        .catch {
            Timber.e(it)
            emit(Resource.error(it, previousData))
        }
}

fun asResourceFlow(block: suspend () -> Unit) = flow<Resource<Unit>> {
    emit(Resource.loading())
    runCatching { block() }
        .onSuccess {
            emit(Resource.success(Unit))
        }
        .onFailure {
            Timber.e(it)
            emit(Resource.error(it))
        }
}
