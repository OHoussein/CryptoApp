package dev.ohoussein.cryptoapp.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(CryptoDB.Schema, context, "CryptoDatabase.db")
}
