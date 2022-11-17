package dev.ohoussein.crypto.domain

import dev.ohoussein.crypto.domain.model.defaultLocale
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val cryptoDomainModule = module {
    factoryOf(::GetCryptoDetails)
    factoryOf(::GetTopCryptoList)
    single {
        defaultLocale
    }
}
