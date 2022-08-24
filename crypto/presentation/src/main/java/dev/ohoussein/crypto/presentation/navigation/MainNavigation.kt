package dev.ohoussein.crypto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.ohoussein.crypto.presentation.components.CryptoDetailsScreen
import dev.ohoussein.crypto.presentation.components.CryptoListScreen
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import dev.ohoussein.cryptoapp.common.navigation.ExternalRouter
import timber.log.Timber

@Composable
fun CryptoAppNavigation(
    externalRouter: ExternalRouter,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = NavPath.HOME) {
        composable(NavPath.HOME) {
            val viewModel = hiltViewModel<CryptoListViewModel>()
            CryptoListScreen(
                viewModel = viewModel,
                onClick = {
                    Timber.d("On item clicked ${it.base.name}")
                    navController.navigate(NavPath.CryptoDetailsPath.path(it.base.id))
                }
            )
        }
        composable(
            NavPath.CryptoDetailsPath.PATH,
            arguments = listOf(
                navArgument(NavPath.CryptoDetailsPath.ARG_CRYPTO_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel = hiltViewModel<CryptoDetailsViewModel>()
            CryptoDetailsScreen(
                viewModel = viewModel,
                externalRouter = externalRouter,
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}
