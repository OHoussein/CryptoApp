import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cryptoapp.shared.presentation.generated.resources.Res
import cryptoapp.shared.presentation.generated.resources.app_icon_256
import dev.ohoussein.cryptoapp.presentation.sharedPresentationModules
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin

fun createApp() {
    startKoin {
        modules(sharedPresentationModules)
    }
    application {
        Window(
            title = "CryptoApp",
            onCloseRequest = ::exitApplication,
            icon = painterResource(Res.drawable.app_icon_256),
        ) {
            SharedApp()
        }
    }
}
