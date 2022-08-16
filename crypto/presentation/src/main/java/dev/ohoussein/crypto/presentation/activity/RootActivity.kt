package dev.ohoussein.crypto.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.ohoussein.crypto.presentation.navigation.CryptoAppNavigation
import dev.ohoussein.cryptoapp.common.navigation.ExternalRouter
import javax.inject.Inject

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    @Inject
    lateinit var externalRouter: ExternalRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoAppNavigation(
                externalRouter = externalRouter,
            )
        }
    }
}
