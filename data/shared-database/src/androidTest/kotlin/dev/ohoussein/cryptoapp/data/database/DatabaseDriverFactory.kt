package dev.ohoussein.cryptoapp.data.database

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.ohoussein.cryptoapp.database.CryptoDB

fun createDatabase(): CryptoDB {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    CryptoDB.Schema.create(driver)
    return CryptoDB(driver)
}
