package dev.ohoussein.cryptoapp.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(CryptoDB.Schema, "CryptoDatabase.db")
}

