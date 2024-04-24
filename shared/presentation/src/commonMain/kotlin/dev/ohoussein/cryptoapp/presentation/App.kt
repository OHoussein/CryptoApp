import androidx.compose.runtime.Composable
import dev.ohoussein.cryptoapp.crypto.presentation.list.CryptoListScreen
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun SharedApp() {
    KoinContext {
        CryptoListScreen(
            viewModel = koinInject(),
            navigateToCryptoDetails = {
                println("Navigate to $it")
            }
        )
    }
}
