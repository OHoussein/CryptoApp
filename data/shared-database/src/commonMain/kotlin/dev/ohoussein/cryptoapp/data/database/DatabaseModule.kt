package dev.ohoussein.cryptoapp.data.database

import dev.ohoussein.cryptoapp.data.database.crypto.DBModelMapper
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val databaseModule = module {
    includes(platformDatabaseModule)

    factoryOf(::DBModelMapper)
}

expect val platformDatabaseModule: Module
