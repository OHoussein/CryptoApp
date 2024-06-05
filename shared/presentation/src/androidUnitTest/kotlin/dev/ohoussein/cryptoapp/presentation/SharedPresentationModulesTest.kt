package dev.ohoussein.cryptoapp.presentation

import android.content.Context
import dev.ohoussein.cryptoapp.core.formatter.formatModule
import dev.ohoussein.cryptoapp.core.router.routerModule
import dev.ohoussein.cryptoapp.crypto.data.cryptoDataModule
import dev.ohoussein.cryptoapp.crypto.domain.cryptoDomainModule
import dev.ohoussein.cryptoapp.crypto.presentation.di.cryptoModule
import dev.ohoussein.cryptoapp.data.database.databaseModule
import dev.ohoussein.cryptoapp.data.network.networkModule
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import kotlin.test.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.check.checkKoinModules
import org.koin.test.check.checkModules
import org.koin.test.verify.Verify.verify
import org.koin.test.verify.verify

val sharedPresentationModules = module {
    includes(
        formatModule,
        databaseModule,
        networkModule,
        cryptoDomainModule,
        cryptoDataModule,
        routerModule,
    )
    includes(cryptoModule)
}

class SharedPresentationModulesTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkDependencies() {
        sharedPresentationModules.verify(
            extraTypes = listOf(Context::class, HttpClientEngine::class, HttpClientConfig::class)
        )
    }
}