package dev.ohoussein.cryptoapp.presentation

import dev.ohoussein.cryptoapp.core.sharedmodules.sharedModules
import dev.ohoussein.cryptoapp.crypto.presentation.di.cryptoModule
import org.koin.dsl.module

val sharedPresentationModules = module {
    includes(sharedModules)
    includes(cryptoModule)
}
