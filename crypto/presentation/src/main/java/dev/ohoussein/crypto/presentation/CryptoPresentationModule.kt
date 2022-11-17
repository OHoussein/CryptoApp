package dev.ohoussein.crypto.presentation

import dev.ohoussein.crypto.data.cryptoDataModule
import dev.ohoussein.crypto.domain.cryptoDomainModule
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

private val cryptoPresentationModule = module {
    factoryOf(::DomainModelMapper)
    viewModelOf(::CryptoListViewModel)
    viewModelOf(::CryptoDetailsViewModel)
}

val cryptoModule = module {
    includes(cryptoDomainModule)
    includes(cryptoDataModule)
    includes(cryptoPresentationModule)
}
