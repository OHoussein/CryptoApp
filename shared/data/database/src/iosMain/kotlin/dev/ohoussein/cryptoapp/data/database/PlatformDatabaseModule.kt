package dev.ohoussein.cryptoapp.data.database

import app.cash.sqldelight.db.SqlDriver
import dev.ohoussein.cryptoapp.data.database.crypto.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.CryptoDAOImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    factory<SqlDriver> { DatabaseDriverFactory().createDriver() }
    single<CryptoDAO> {
        CryptoDAOImpl(get(), Dispatchers.Default, get())
    }
}
