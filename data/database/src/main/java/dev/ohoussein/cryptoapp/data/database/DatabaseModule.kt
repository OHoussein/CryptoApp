package dev.ohoussein.cryptoapp.data.database

import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(CryptoDatabase::build)
    single {
        get<CryptoDatabase>().cryptoDAO()
    }
    factoryOf(::DbDomainModelMapper)
}
