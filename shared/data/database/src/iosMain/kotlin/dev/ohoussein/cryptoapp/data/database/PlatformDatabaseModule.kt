package dev.ohoussein.cryptoapp.data.database

import dev.ohoussein.cryptoapp.data.database.crypto.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.CryptoDAOImpl
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformDatabaseModule = module {
    singleOf(::buildCryptoDB)
    single<CryptoDAO> {
        CryptoDAOImpl(get(), Dispatchers.Default, get())
    }
}
