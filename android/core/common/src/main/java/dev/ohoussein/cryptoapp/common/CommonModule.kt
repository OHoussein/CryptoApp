package dev.ohoussein.cryptoapp.common

import dev.ohoussein.cryptoapp.common.formatter.ErrorMessageFormatter
import dev.ohoussein.cryptoapp.common.navigation.ExternalRouter
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val commonModule = module {
    factoryOf(::ExternalRouter)
    factoryOf(::ErrorMessageFormatter)
}
