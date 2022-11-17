package dev.ohoussein.cryptoapp.data.network

import dev.ohoussein.cryptoapp.core.DIConstants
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataDebugModule = module {
    single<Array<Interceptor>>(named(DIConstants.Qualifier.HTTP_NETWORK_INTERCEPTOR)) { emptyArray() }
    single<Array<Interceptor>>(named(DIConstants.Qualifier.HTTP_INTERCEPTOR)) {
        arrayOf(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            },
        )
    }
}
