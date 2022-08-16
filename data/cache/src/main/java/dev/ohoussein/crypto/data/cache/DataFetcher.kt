package dev.ohoussein.crypto.data.cache

fun interface DataFetcher<Key : Any, Output : Any> {
    suspend fun fetch(key: Key): Output
}
