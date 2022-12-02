package dev.ohoussein.core.injection

import dev.ohoussein.crypto.presentation.cryptoModule
import dev.ohoussein.cryptoapp.common.commonModule
import dev.ohoussein.cryptoapp.core.formatter.formatModule
import dev.ohoussein.cryptoapp.data.database.databaseModule
import dev.ohoussein.cryptoapp.data.network.dataDebugModule
import dev.ohoussein.cryptoapp.data.network.networkModule
import org.koin.core.module.Module

val cryptoAppModules: List<Module> = listOf(
    cryptoModule,
    commonModule,
    formatModule,
    databaseModule,
    networkModule,
    dataDebugModule,
)
