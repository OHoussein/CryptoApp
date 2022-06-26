package dev.ohoussein.crypto.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.ohoussein.cryptoapp.data.database.DBCrypto
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDAO {

    @Query("SELECT * FROM crypto ORDER BY `order`")
    fun getAll(): Flow<List<DBCrypto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<DBCrypto>): List<Long>
}
