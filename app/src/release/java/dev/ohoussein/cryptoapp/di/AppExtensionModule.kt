package dev.ohoussein.cryptoapp.di

import dev.ohoussein.cryptoapp.config.IAppFlavorSetup
import dev.ohoussein.cryptoapp.config.ReleaseAppSetup
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appExtensionModule = module {
    singleOf(::ReleaseAppSetup) {
        bind<IAppFlavorSetup>()
    }
}
