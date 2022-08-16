package dev.ohoussein.cryptoapp.cacheddata

data class CachedData<T>(
    val origin: DataOrigin,
    val data: T,
    val isLoading: Boolean,
) {

    fun <T2> map(newData: T2) = CachedData(
        isLoading = isLoading,
        origin = origin,
        data = newData,
    )

    companion object {
        fun <T> cached(data: T, isLoading: Boolean) = CachedData(
            data = data,
            origin = DataOrigin.CACHE,
            isLoading = isLoading,
        )

        fun <T> fresh(data: T) = CachedData(
            data = data,
            origin = DataOrigin.FRESH,
            isLoading = false,
        )
    }
}

enum class DataOrigin {
    CACHE,
    FRESH,
}
