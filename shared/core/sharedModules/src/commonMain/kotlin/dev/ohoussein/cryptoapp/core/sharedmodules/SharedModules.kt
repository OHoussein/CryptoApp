package dev.ohoussein.cryptoapp.core.sharedmodules

import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.core.formatter.formatModule
import dev.ohoussein.cryptoapp.crypto.data.cryptoDataModule
import dev.ohoussein.cryptoapp.crypto.domain.cryptoDomainModule
import dev.ohoussein.cryptoapp.data.database.databaseModule
import dev.ohoussein.cryptoapp.data.network.networkModule
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module

val sharedModules: List<Module> = listOf(
    formatModule,
    databaseModule,
    networkModule,
    cryptoDomainModule,
    cryptoDataModule,
)

private val koin: Koin by lazy { startKoin() }

// used only for iOS
private fun startKoin(): Koin {
    println("Wiss: startKoin")
    return startKoin {
        modules(sharedModules)
    }.koin
}

val getTopCryptoList: GetTopCryptoList get() = koin.get()
