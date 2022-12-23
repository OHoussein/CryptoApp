package dev.ohoussein.cryptoapp.crypto.domain

import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.model.defaultLocale
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetTopCryptoListUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val cryptoDomainModule = module {
    factoryOf(::GetCryptoDetailsUseCase)
    factoryOf(::GetTopCryptoListUseCase)
    single {
        defaultLocale
    }
    factory(named("languageCode")) { get<Locale>().languageCode }
}
