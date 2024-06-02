package dev.ohoussein.cryptoapp.core.sharedmodules

import dev.ohoussein.cryptoapp.core.formatter.PercentFormatter
import dev.ohoussein.cryptoapp.core.formatter.PriceFormatter
import dev.ohoussein.cryptoapp.core.formatter.formatModule
import dev.ohoussein.cryptoapp.core.router.routerModule
import dev.ohoussein.cryptoapp.crypto.data.cryptoDataModule
import dev.ohoussein.cryptoapp.crypto.domain.cryptoDomainModule
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetTopCryptoListUseCase
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
    routerModule,
)

private val koin: Koin by lazy { startKoin() }

// used only for iOS
private fun startKoin(): Koin {
    return startKoin {
        modules(sharedModules)
    }.koin
}

val getTopCryptoListUseCase: GetTopCryptoListUseCase get() = koin.get()
val getCryptoDetailsUseCase: GetCryptoDetailsUseCase get() = koin.get()
val getPriceFormatter: PriceFormatter get() = koin.get()
val getPercentFormatter: PercentFormatter get() = koin.get()
val getLocale: Locale get() = koin.get()
