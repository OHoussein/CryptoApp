package dev.ohoussein.cryptoapp.config

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

class DebuggableAppSetup : IAppFlavorSetup {

    override fun setup(app: Application) {
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(app)
    }
}
