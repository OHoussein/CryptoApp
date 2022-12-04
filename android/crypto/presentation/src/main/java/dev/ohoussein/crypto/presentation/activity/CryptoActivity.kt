package dev.ohoussein.crypto.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.ohoussein.crypto.presentation.ui.navigation.CryptoAppNavigation
import dev.ohoussein.cryptoapp.common.navigation.ExternalRouter
import org.koin.android.ext.android.inject

class CryptoActivity : ComponentActivity() {

    private val externalRouter: ExternalRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoAppNavigation(
                externalRouter = externalRouter,
            )
        }
    }
}
