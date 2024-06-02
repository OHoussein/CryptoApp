package dev.ohoussein.cryptoapp.data.database

import app.cash.sqldelight.db.SqlDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

fun buildCryptoDB(driver: SqlDriver): CryptoDB {
    runCatching { CryptoDB.Schema.create(driver) }
    return CryptoDB(driver)
}
