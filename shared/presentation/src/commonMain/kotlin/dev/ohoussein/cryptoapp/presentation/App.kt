import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.ohoussein.cryptoapp.crypto.presentation.CryptoFeatNavPath
import dev.ohoussein.cryptoapp.crypto.presentation.nav.cryptoAppNavigation
import org.koin.compose.KoinContext

@Composable
fun SharedApp() {
    KoinContext {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = CryptoFeatNavPath.HOME,
        ) {
            cryptoAppNavigation(navController)
        }
    }
}
