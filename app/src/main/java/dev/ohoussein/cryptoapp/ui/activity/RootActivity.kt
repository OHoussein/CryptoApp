package dev.ohoussein.cryptoapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.ohoussein.cryptoapp.ui.core.mapper.ErrorMessageMapper
import dev.ohoussein.cryptoapp.ui.core.util.ExternalNavigator
import dev.ohoussein.cryptoapp.ui.navigation.CryptoAppNavigation
import javax.inject.Inject

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    @Inject
    lateinit var errorMessageMapper: ErrorMessageMapper

    @Inject
    lateinit var externalNavigator: ExternalNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoAppNavigation(
                errorMessageMapper = errorMessageMapper,
                externalNavigator = externalNavigator,
            )
        }
    }
}