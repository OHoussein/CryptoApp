package dev.ohoussein.cryptoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dev.ohoussein.cryptoapp.ui.core.mapper.ErrorMessageMapper
import dev.ohoussein.cryptoapp.ui.core.util.ExternalNavigator
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.components.CryptoDetailsScreen
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.components.CryptoListScreen
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.HomeViewModel
import timber.log.Timber

@Composable
fun CryptoAppNavigation(
    errorMessageMapper: ErrorMessageMapper,
    externalNavigator: ExternalNavigator,
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
        composable(NavPath.CryptoDetailsPath.PATH, arguments = listOf(
            navArgument(NavPath.CryptoDetailsPath.ARG_CRYPTO_ID) {
                type = NavType.StringType
            }
        )) { backStackEntry ->

            val viewModel = hiltViewModel<CryptoDetailsViewModel>()
            backStackEntry.arguments?.getString(NavPath.CryptoDetailsPath.ARG_CRYPTO_ID)
                ?.let { cryptoId ->
                    CryptoDetailsScreen(
                        viewModel = viewModel,
                        cryptoId = cryptoId,
                        errorMessageMapper = errorMessageMapper,
                        externalNavigator = externalNavigator,
                        onBackClicked = { navController.popBackStack() }
                    )
                }
        }
    }
}
