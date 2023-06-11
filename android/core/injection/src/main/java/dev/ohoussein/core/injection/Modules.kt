package dev.ohoussein.core.injection

import dev.ohoussein.crypto.presentation.cryptoPresentationModule
import dev.ohoussein.cryptoapp.common.commonModule
import dev.ohoussein.cryptoapp.core.sharedmodules.sharedModules
import org.koin.core.module.Module

val androidAppModules: List<Module> = listOf(
    commonModule,
    cryptoPresentationModule,
) + sharedModules
