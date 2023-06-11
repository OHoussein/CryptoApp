@file:Suppress("LongParameterList")

package dev.ohoussein.crypto.presentation.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.reducer.CryptoListIntent
import dev.ohoussein.crypto.presentation.ui.components.CryptoItem
import dev.ohoussein.crypto.presentation.ui.debug.DataPreview.previewListCrypto
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import dev.ohoussein.cryptoapp.common.resource.DataStatus
import dev.ohoussein.cryptoapp.core.designsystem.R.string.core_retry
import dev.ohoussein.cryptoapp.core.designsystem.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.core.designsystem.base.StateError
import dev.ohoussein.cryptoapp.core.designsystem.base.StateLoading
import dev.ohoussein.cryptoapp.core.designsystem.theme.CryptoAppTheme

@Suppress("TopLevelPropertyNaming")
const val CryptoListTestTag = "CryptoListTestTag"

@Suppress("TopLevelPropertyNaming")
const val CryptoItemTestTag = "CryptoItemTestTag"

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
                        .testTag(CryptoItemTestTag + crypto.base.id)
                        .fillMaxWidth(),
                    crypto = crypto,
                    onClick = onClick,
                )
            }
        }
        PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
fun CryptoListStateScreen(
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
                                Text(text = stringResource(id = core_retry))
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

@Composable
fun CryptoListScreen(
    viewModel: CryptoListViewModel,
    onClick: (Crypto) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.dispatch(CryptoListIntent.ScreenOpened)
    }

    CryptoAppScaffold {
        CryptoListStateScreen(
            cryptoList = state.cryptoList,
            isLoading = state.status == DataStatus.Loading,
            error = (state.status as? DataStatus.Error)?.message,
            onClick = onClick,
            onRefresh = { viewModel.dispatch(CryptoListIntent.Refresh) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCryptoList(darkTheme: Boolean = true) {
    CryptoAppTheme(darkTheme = darkTheme) {
        CryptoList(
            cryptoList = previewListCrypto,
            onClick = {},
            onRefresh = {},
            isRefreshing = false,
        )
    }
}
