package dev.ohoussein.cryptoapp

import android.app.Application
import dev.ohoussein.cryptoapp.config.IAppFlavorSetup
import dev.ohoussein.cryptoapp.di.appExtensionModule
import dev.ohoussein.cryptoapp.presentation.sharedPresentationModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val appSetup: IAppFlavorSetup by inject()

    override fun onCreate() {
        super.onCreate()
        startDI()
        appSetup.setup(this)
    }

    private fun startDI() {
        startKoin {
            androidContext(this@App)
            modules(appExtensionModule)
            modules(sharedPresentationModules)
        }
    }
}
