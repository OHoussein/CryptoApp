package dev.ohoussein.crypto.data

import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.repository.CryptoRepository
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val cryptoDataModule = module {
    singleOf(::CryptoRepository) { bind<ICryptoRepository>() }
    singleOf(ApiCryptoService::create)
    factoryOf(::ApiDomainModelMapper)
}