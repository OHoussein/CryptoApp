package dev.ohoussein.core.test

import android.app.Application
import dev.ohoussein.core.injection.cryptoAppModules
import dev.ohoussein.core.test.di.mockedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinTestApplication)
            allowOverride(true)
            modules(cryptoAppModules)
            modules(mockedModule)
        }
    }
}
