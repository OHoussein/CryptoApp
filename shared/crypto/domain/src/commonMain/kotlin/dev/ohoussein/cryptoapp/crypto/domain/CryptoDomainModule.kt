package dev.ohoussein.cryptoapp.crypto.domain

import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.model.defaultLocale
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetails
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val cryptoDomainModule = module {
    factoryOf(::GetCryptoDetails)
    factoryOf(::GetTopCryptoList)
    single {
        defaultLocale
    }
    factory(named("languageCode")) { get<Locale>().languageCode }
}
