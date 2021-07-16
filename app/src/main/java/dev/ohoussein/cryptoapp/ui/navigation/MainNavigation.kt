package dev.ohoussein.cryptoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.ohoussein.cryptoapp.ui.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.ui.core.mapper.ErrorMessageMapper
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.Resource
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.components.CryptoListScreen
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.HomeViewModel

@Composable
fun CryptoAppNavigation(
    errorMessageMapper: ErrorMessageMapper,
    onClick: (Crypto) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = NavPath.HOME) {
        composable(NavPath.HOME) {
            val viewModel = hiltViewModel<HomeViewModel>()

            val cryptoListState: Resource<List<Crypto>> by viewModel.topCryptoList.observeAsState(
                Resource.loading()
            )
            //there's a cast issue here, that's why I used an intermediate variable
            val data: Resource<List<Crypto>> = cryptoListState

            CryptoAppScaffold {
                CryptoListScreen(
                    cryptoListState = data,
                    errorMessageMapper = errorMessageMapper,
                    onClick = onClick,
                    onRefresh = { viewModel.load(refresh = true) }
                )
            }
        }
    }
}
