package dev.ohoussein.cryptoapp.data.database.crypto.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCryptoDetails
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CryptoDAO {

    @Query("SELECT * FROM crypto ORDER BY `order`")
    abstract fun getAll(): Flow<List<DBCrypto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(items: List<DBCrypto>): List<Long>

    @Query("DELETE FROM crypto")
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun replace(items: List<DBCrypto>): List<Long> {
        deleteAll()
        return insert(items)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(item: DBCryptoDetails): Long

    @Query("SELECT * FROM crypto_details where id = :id")
    abstract fun getCryptoDetails(id: String): Flow<DBCryptoDetails?>
}
