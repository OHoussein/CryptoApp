package dev.ohoussein.crypto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dev.ohoussein.core.formatter.ErrorMessageFormatter
import dev.ohoussein.crypto.presentation.components.CryptoDetailsScreen
import dev.ohoussein.crypto.presentation.components.CryptoListScreen
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.crypto.presentation.viewmodel.HomeViewModel
import dev.ohoussein.cryptoapp.presentation.navigation.ExternalRouter
import timber.log.Timber

@Composable
fun CryptoAppNavigation(
    errorMessageMapper: ErrorMessageFormatter,
    externalRouter: ExternalRouter,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = NavPath.HOME) {
        composable(NavPath.HOME) {
            val viewModel = hiltViewModel<HomeViewModel>()
            CryptoListScreen(
                viewModel = viewModel,
                errorMessageMapper = errorMessageMapper,
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
        ) { backStackEntry ->

            val viewModel = hiltViewModel<CryptoDetailsViewModel>()
            backStackEntry.arguments?.getString(NavPath.CryptoDetailsPath.ARG_CRYPTO_ID)
                ?.let { cryptoId ->
                    CryptoDetailsScreen(
                        viewModel = viewModel,
                        cryptoId = cryptoId,
                        errorMessageFormatter = errorMessageMapper,
                        externalRouter = externalRouter,
                        onBackClicked = { navController.popBackStack() }
                    )
                }
        }
    }
}
