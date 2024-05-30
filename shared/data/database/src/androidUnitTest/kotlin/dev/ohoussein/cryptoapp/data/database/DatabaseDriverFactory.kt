package dev.ohoussein.cryptoapp.data.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

fun createDatabase(): CryptoDB {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    CryptoDB.Schema.create(driver)
    return CryptoDB(driver)
}
