package dev.ohoussein.cryptoapp.common.resource

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */

data class Resource<out T>(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: Throwable, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

/**
 * Status of a resource that is provided to the UI.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
