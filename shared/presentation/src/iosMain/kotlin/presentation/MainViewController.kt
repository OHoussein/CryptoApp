package presentation

import SharedApp
import androidx.compose.ui.window.ComposeUIViewController
import dev.ohoussein.cryptoapp.presentation.sharedPresentationModules
import org.koin.compose.KoinApplication

fun mainViewController() = ComposeUIViewController {
    KoinApplication(application = {
        modules(sharedPresentationModules)
    }) {
        SharedApp()
    }
}
