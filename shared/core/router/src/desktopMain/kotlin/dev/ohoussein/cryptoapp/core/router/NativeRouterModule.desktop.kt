package dev.ohoussein.cryptoapp.core.router

import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val nativeRouterModule: Module = module {
    factoryOf<Router>(::RouterImpl)
}
