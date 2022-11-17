package dev.ohoussein.crypto.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.ohoussein.crypto.presentation.NavPath
import dev.ohoussein.crypto.presentation.NavPath.CryptoDetailsPath
import dev.ohoussein.crypto.presentation.ui.CryptoDetailsScreen
import dev.ohoussein.crypto.presentation.ui.CryptoListScreen
import dev.ohoussein.cryptoapp.common.navigation.ExternalRouter
import org.koin.androidx.compose.getViewModel
import timber.log.Timber

@Composable
fun CryptoAppNavigation(
    externalRouter: ExternalRouter,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = NavPath.HOME) {
        composable(NavPath.HOME) {
            CryptoListScreen(
                viewModel = getViewModel(),
                onClick = {
                    Timber.d("On item clicked ${it.base.name}")
                    navController.navigate(CryptoDetailsPath.path(it.base.id))
                }
            )
        }
        composable(
            CryptoDetailsPath.PATH,
            arguments = listOf(
                navArgument(CryptoDetailsPath.ARG_CRYPTO_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            CryptoDetailsScreen(
                viewModel = getViewModel(),
                externalRouter = externalRouter,
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}
