import androidx.compose.runtime.*
import dev.ohoussein.cryptoapp.crypto.presentation.details.CryptoDetailsScreen
import dev.ohoussein.cryptoapp.crypto.presentation.list.CryptoListScreen
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun SharedApp() {
    KoinContext {
        var selectedCryptoId by remember {
            mutableStateOf<String?>(null)
        }
        if (selectedCryptoId != null) {
            CryptoDetailsScreen(
                viewModel = koinInject { parametersOf(selectedCryptoId) },
                onBackClicked = {
                    selectedCryptoId = null
                }
            )
        } else {
            CryptoListScreen(
                viewModel = koinInject(),
                navigateToCryptoDetails = {
                    selectedCryptoId = it.info.id
                    println("Navigate to $it")
                }
            )
        }
    }
}
