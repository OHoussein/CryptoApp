package dev.ohoussein.cryptoapp.data.network

import dev.ohoussein.cryptoapp.data.network.crypto.service.ApiCryptoService
import dev.ohoussein.cryptoapp.data.network.crypto.service.ApiCryptoServiceImpl
import org.koin.dsl.module

val networkModule = module {
    single {
        NetworkBuilder.httpClient()
    }

    single<ApiCryptoService> {
        ApiCryptoServiceImpl(get())
    }
}
