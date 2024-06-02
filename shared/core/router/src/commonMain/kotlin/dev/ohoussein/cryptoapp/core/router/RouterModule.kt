package dev.ohoussein.cryptoapp.core.router

import org.koin.core.module.Module
import org.koin.dsl.module

val routerModule = module {
    includes(nativeRouterModule)
}

expect val nativeRouterModule: Module
