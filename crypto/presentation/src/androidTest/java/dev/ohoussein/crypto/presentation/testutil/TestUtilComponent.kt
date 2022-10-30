package dev.ohoussein.crypto.presentation.testutil

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
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
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = path) {
        composable(path, arguments = arguments) {
            content()
        }
    }
}
