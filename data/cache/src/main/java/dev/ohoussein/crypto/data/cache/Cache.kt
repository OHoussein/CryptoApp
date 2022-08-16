package dev.ohoussein.cryptoapp.data.cache

import kotlinx.coroutines.flow.Flow

interface Cache<Key, Value> {

    companion object {
        fun <Key : Any, Value : Any> of(
            reader: (Key) -> Flow<Value?>,
            writer: suspend (Key, Value) -> Unit,
        ): Cache<Key, Value> = CacheImpl(
            realReader = reader,
            realWriter = writer,
        )
    }

    fun reader(key: Key): Flow<Value?>

    suspend fun write(key: Key, value: Value)
}

internal class CacheImpl<Key : Any, Value : Any>(
    val realReader: (Key) -> Flow<Value?>,
    val realWriter: suspend (Key, Value) -> Unit,
) : Cache<Key, Value> {

    override fun reader(key: Key): Flow<Value?> = realReader(key)

    override suspend fun write(key: Key, value: Value) = realWriter(key, value)
}
