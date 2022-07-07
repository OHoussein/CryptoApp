package dev.ohoussein.crypto.presentation.ui.testutil

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * A NavHost than contains a single composable
 * We need this to be able to get the ViewModel view hiltViewModel<XXX>
 */
@Composable
fun TestNavHost(
        path: String,
        content: @Composable () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = path) {
        composable(path) {
            content()
        }
    }
}