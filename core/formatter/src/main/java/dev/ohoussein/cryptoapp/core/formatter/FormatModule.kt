package dev.ohoussein.cryptoapp.core.formatter

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val formatModule = module {
    factoryOf(::PriceFormatter)
    factoryOf(::PercentFormatter)
    factoryOf(::ErrorMessageFormatter)
}
