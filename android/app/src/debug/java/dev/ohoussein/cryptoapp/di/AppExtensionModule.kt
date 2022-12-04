package dev.ohoussein.cryptoapp.di

import dev.ohoussein.cryptoapp.config.DebuggableAppSetup
import dev.ohoussein.cryptoapp.config.IAppFlavorSetup
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appExtensionModule = module {
    singleOf(::DebuggableAppSetup) {
        bind<IAppFlavorSetup>()
    }
}
