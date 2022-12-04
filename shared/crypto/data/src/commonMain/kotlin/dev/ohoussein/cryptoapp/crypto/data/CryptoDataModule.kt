package dev.ohoussein.cryptoapp.crypto.data

import dev.ohoussein.cryptoapp.crypto.data.mapper.ApiDomainModelMapper
import dev.ohoussein.cryptoapp.crypto.data.repository.CryptoRepository
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cryptoDataModule = module {
    singleOf(::CryptoRepository) { bind<ICryptoRepository>() }
    factoryOf(::ApiDomainModelMapper)
}
