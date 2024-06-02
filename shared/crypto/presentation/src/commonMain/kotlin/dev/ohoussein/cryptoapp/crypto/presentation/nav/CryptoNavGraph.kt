package dev.ohoussein.cryptoapp.crypto.presentation.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.ohoussein.cryptoapp.crypto.presentation.CryptoFeatNavPath
import dev.ohoussein.cryptoapp.crypto.presentation.CryptoFeatNavPath.CryptoDetailsPath.ARG_CRYPTO_ID
import dev.ohoussein.cryptoapp.crypto.presentation.details.CryptoDetailsScreen
import dev.ohoussein.cryptoapp.crypto.presentation.list.CryptoListScreen
import dev.ohoussein.cryptoapp.designsystem.base.StateError

fun NavGraphBuilder.cryptoAppNavigation(navController: NavHostController) {
    homeEntry(navController)
    cryptoDetailsEntry(navController)
}

private fun NavGraphBuilder.homeEntry(navController: NavHostController) {
    composable(CryptoFeatNavPath.HOME) {
        CryptoListScreen(
            navigateToCryptoDetails = {
                navController.navigate(CryptoFeatNavPath.CryptoDetailsPath.path(it.info.id))
            }
        )
    }
}

private fun NavGraphBuilder.cryptoDetailsEntry(navController: NavHostController) {
    composable(
        CryptoFeatNavPath.CryptoDetailsPath.PATH,
        arguments = listOf(
            navArgument(ARG_CRYPTO_ID) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        entry.arguments?.getString(ARG_CRYPTO_ID)?.let {
            CryptoDetailsScreen(
                cryptoId = entry.arguments!!.getString(ARG_CRYPTO_ID)!!,
                onBackClicked = { navController.popBackStack() }
            )
        } ?: run {
            StateError(
                message = "Invalid arguments",
                onRetryClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
