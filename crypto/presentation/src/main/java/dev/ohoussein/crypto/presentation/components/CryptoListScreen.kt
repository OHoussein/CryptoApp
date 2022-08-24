@file:Suppress("LongParameterList")

package dev.ohoussein.crypto.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.ohoussein.crypto.presentation.debug.DataPreview.previewListCrypto
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.reducer.CryptoListIntent
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import dev.ohoussein.cryptoapp.common.resource.Status
import dev.ohoussein.cryptoapp.core.designsystem.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.core.designsystem.base.StateError
import dev.ohoussein.cryptoapp.core.designsystem.base.StateLoading
import dev.ohoussein.cryptoapp.core.designsystem.theme.CryptoAppTheme
import dev.ohoussein.cryptoapp.crypto.presentation.R

@Suppress("TopLevelPropertyNaming")
const val CryptoListTestTag = "CryptoListTestTag"

@Suppress("TopLevelPropertyNaming")
const val CryptoItemTestTag = "CryptoItemTestTag"

@Composable
fun CryptoList(
    modifier: Modifier = Modifier,
    cryptoList: List<Crypto>,
    onClick: (Crypto) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = onRefresh,
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
                                Text(text = stringResource(id = R.string.core_retry))
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

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun CryptoListScreen(
    viewModel: CryptoListViewModel,
    onClick: (Crypto) -> Unit,
) {
    val cryptoList by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.dispatch(CryptoListIntent.ScreenOpened)
    }

    CryptoAppScaffold {
        CryptoListStateScreen(
            cryptoList = cryptoList.cryptoList.data,
            isLoading = cryptoList.cryptoList.status == Status.LOADING,
            error = cryptoList.cryptoList.error,
            onClick = onClick,
            onRefresh = { viewModel.dispatch(CryptoListIntent.Refresh) }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewCryptoList() {
    CryptoAppTheme {
        CryptoList(
            cryptoList = previewListCrypto,
            onClick = {},
            onRefresh = {},
            isRefreshing = false,
        )
    }
}
