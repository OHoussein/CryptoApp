package dev.ohoussein.cryptoapp.data.network

import dev.ohoussein.cryptoapp.data.network.builder.NetworkBuilder
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    factoryOf(NetworkBuilder::createConverter)
    single {
        NetworkBuilder.createOkHttp(
            get(qualifier = named(DIConstants.Qualifier.HTTP_INTERCEPTOR)),
            get(qualifier = named(DIConstants.Qualifier.HTTP_NETWORK_INTERCEPTOR)),
        )
    }
    single {
        NetworkBuilder.createRetrofit(
            okHttpClient = get(),
            converterFactory = get(),
        )
    }
}