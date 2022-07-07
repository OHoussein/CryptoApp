package dev.ohoussein.crypto.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.ohoussein.crypto.presentation.navigation.CryptoAppNavigation
import dev.ohoussein.cryptoapp.presentation.mapper.ErrorMessageMapper
import dev.ohoussein.cryptoapp.presentation.util.ExternalNavigator
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
