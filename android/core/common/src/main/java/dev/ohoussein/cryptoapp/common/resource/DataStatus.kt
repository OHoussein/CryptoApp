package dev.ohoussein.cryptoapp.common.resource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

sealed class DataStatus {
    object Success : DataStatus()
    data class Error(val message: String) : DataStatus()
    object Loading : DataStatus()
    object Initial : DataStatus()
}

fun asDataStatusFlow(
    errorMessageMapper: (Throwable) -> String,
    block: suspend () -> Unit,
): Flow<DataStatus> = flow {
    emit(DataStatus.Loading)
    runCatching { block() }
        .onSuccess {
            emit(DataStatus.Success)
        }
        .onFailure {
            Timber.e(it)
            emit(DataStatus.Error(errorMessageMapper(it)))
        }
}
