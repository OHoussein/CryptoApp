package dev.ohoussein.cryptoapp.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.ohoussein.cryptoapp.ui.core.mapper.ErrorMessageMapper
import dev.ohoussein.cryptoapp.ui.navigation.CryptoAppNavigation
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    @Inject
    lateinit var errorMessageMapper: ErrorMessageMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoAppNavigation(
                errorMessageMapper = errorMessageMapper,
                onClick = {
                    Timber.d("On repo clicked ${it.name}")
                },
            )
        }
    }
}