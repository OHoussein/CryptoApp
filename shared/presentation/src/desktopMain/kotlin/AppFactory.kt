import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.ohoussein.cryptoapp.presentation.sharedPresentationModules
import org.koin.core.context.startKoin

fun createApp() {
    startKoin {
        modules(sharedPresentationModules)
    }
    application {
        Window(
            title = "CryptoApp",
            onCloseRequest = ::exitApplication,
        ) {
            SharedApp()
        }
    }
}
