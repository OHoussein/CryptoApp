package dev.ohoussein.cryptoapp.data.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(CryptoDB.Schema, "CryptoDatabase.db")
}

fun buildCryptoDB(): CryptoDB {
    val driver = DatabaseDriverFactory().createDriver()
    return CryptoDB(driver)
}
