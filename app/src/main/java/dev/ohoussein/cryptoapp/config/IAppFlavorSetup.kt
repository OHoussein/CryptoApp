package dev.ohoussein.cryptoapp.config

import android.app.Application

/**
 * Setup the app depending on th flavor (release or debug)
 */
interface IAppFlavorSetup {
    fun setup(app: Application)
}
