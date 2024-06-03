package dev.ohoussein.cryptoapp

import android.app.Application
import dev.ohoussein.cryptoapp.presentation.sharedPresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() {
        startKoin {
            androidContext(this@App)
            modules(sharedPresentationModules)
        }
    }
}
