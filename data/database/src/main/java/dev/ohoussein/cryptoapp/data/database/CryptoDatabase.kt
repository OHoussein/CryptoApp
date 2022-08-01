package dev.ohoussein.cryptoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.model.DBCrypto

private const val DB_VERSION = 1
private const val DB_NAME = "CryptoApp.db"

@Database(entities = [DBCrypto::class], version = DB_VERSION)
abstract class CryptoDatabase : RoomDatabase() {

    abstract fun cryptoDAO(): CryptoDAO

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, CryptoDatabase::class.java, DB_NAME).build()

        fun buildForTesting(context: Context) =
            Room.inMemoryDatabaseBuilder(context, CryptoDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }
}
