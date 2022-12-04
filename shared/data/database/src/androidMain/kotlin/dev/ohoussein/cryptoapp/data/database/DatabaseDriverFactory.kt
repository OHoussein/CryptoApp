package dev.ohoussein.cryptoapp.data.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(CryptoDB.Schema, context, "CryptoDatabase.db")
}

fun buildCryptoDB(context: Context): CryptoDB {
    val driver = DatabaseDriverFactory(context).createDriver()
    return CryptoDB(driver)
}
