package dev.ohoussein.cryptoapp.crypto.presentation.di

import dev.ohoussein.cryptoapp.crypto.data.cryptoDataModule
import dev.ohoussein.cryptoapp.crypto.domain.cryptoDomainModule
import dev.ohoussein.cryptoapp.crypto.presentation.list.CryptoListViewModel
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val cryptoPresentationModule = module {
    factoryOf(::DomainModelMapper)
    factoryOf(::CryptoListViewModel)
}

val cryptoModule = module {
    includes(cryptoDomainModule)
    includes(cryptoDataModule)
    includes(cryptoPresentationModule)
}
