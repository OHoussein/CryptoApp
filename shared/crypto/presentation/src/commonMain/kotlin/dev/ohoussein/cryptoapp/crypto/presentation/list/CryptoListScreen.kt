package dev.ohoussein.cryptoapp.crypto.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cryptoapp.shared.crypto.presentation.generated.resources.Res
import cryptoapp.shared.crypto.presentation.generated.resources.retry
import dev.ohoussein.cryptoapp.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoItem
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.designsystem.base.StateError
import dev.ohoussein.cryptoapp.designsystem.base.StateLoading
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.core.Koin

const val CryptoListTestTag = "CryptoListTestTag"
const val CryptoItemTestTag = "CryptoItemTestTag"

@Composable
fun CryptoListScreen(
    // TODO This is a workaround while the viewModel builder from koin is ready
    koin: Koin = getKoin(),
    viewModel: CryptoListViewModel = viewModel { koin.get() },
    navigateToCryptoDetails: (Crypto) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    CryptoAppScaffold {
        CryptoListScreenContent(
            cryptoList = state.cryptoList,
            isLoading = state.status == DataStatus.Loading,
            error = (state.status as? DataStatus.Error)?.message,
            onClick = navigateToCryptoDetails,
            onRefresh = { viewModel.dispatch(CryptoListEvents.Refresh) }
        )
    }
}

@Composable
private fun CryptoListScreenContent(
    modifier: Modifier = Modifier,
    cryptoList: List<Crypto>?,
    isLoading: Boolean,
    error: String?,
    onClick: (Crypto) -> Unit,
    onRefresh: () -> Unit,
) {
    when {
        cryptoList != null -> {
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CryptoList(
                    cryptoList = cryptoList,
                    onClick = onClick,
                    onRefresh = onRefresh,
                    isRefreshing = isLoading
                )
                if (error != null) {
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        action = {
                            TextButton(onClick = onRefresh) {
                                Text(text = stringResource(Res.string.retry))
                            }
                        }
                    ) {
                        Text(text = error)
                    }
                }
            }
        }

        error != null -> {
            StateError(
                message = error,
                onRetryClick = onRefresh,
            )
        }

        else -> {
            StateLoading()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CryptoList(
    modifier: Modifier = Modifier,
    cryptoList: List<Crypto>,
    onClick: (Crypto) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    val pullRefreshState = rememberPullRefreshState(isRefreshing, onRefresh)

    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .testTag(CryptoListTestTag)

        ) {
            items(cryptoList) { crypto ->
                CryptoItem(
                    modifier = Modifier
                        .testTag(CryptoItemTestTag + crypto.info.id)
                        .fillMaxWidth(),
                    crypto = crypto,
                    onClick = onClick,
                )
            }
        }
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}
