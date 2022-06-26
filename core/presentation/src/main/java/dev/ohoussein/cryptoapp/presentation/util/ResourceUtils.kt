package dev.ohoussein.cryptoapp.presentation.util

import dev.ohoussein.cryptoapp.presentation.model.Resource
import kotlinx.coroutines.flow.*
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