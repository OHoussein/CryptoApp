package dev.ohoussein.cryptoapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.ohoussein.cryptoapp.data.Config.DB_NAME
import dev.ohoussein.cryptoapp.data.Config.DB_VERSION
import dev.ohoussein.cryptoapp.data.database.dao.CryptoDAO

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
