package dev.ohoussein.cryptoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.ohoussein.cryptoapp.config.IAppFlavorSetup
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var appSetup: IAppFlavorSetup

    override fun onCreate() {
        super.onCreate()

        appSetup.setup(this)
    }
}
