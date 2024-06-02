package dev.ohoussein.cryptoapp.presentation

import dev.ohoussein.cryptoapp.core.formatter.formatModule
import dev.ohoussein.cryptoapp.core.router.routerModule
import dev.ohoussein.cryptoapp.crypto.data.cryptoDataModule
import dev.ohoussein.cryptoapp.crypto.domain.cryptoDomainModule
import dev.ohoussein.cryptoapp.crypto.presentation.di.cryptoModule
import dev.ohoussein.cryptoapp.data.database.databaseModule
import dev.ohoussein.cryptoapp.data.network.networkModule
import org.koin.dsl.module

val sharedPresentationModules = module {
    includes(
        formatModule,
        databaseModule,
        networkModule,
        cryptoDomainModule,
        cryptoDataModule,
        routerModule,
    )
    includes(cryptoModule)
}
