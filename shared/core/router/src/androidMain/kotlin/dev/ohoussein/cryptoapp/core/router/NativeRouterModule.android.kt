package dev.ohoussein.cryptoapp.core.router

import org.koin.core.module.Module
import org.koin.dsl.module

actual val nativeRouterModule: Module = module {
    factory<Router> { RouterImpl(get()) }
}
