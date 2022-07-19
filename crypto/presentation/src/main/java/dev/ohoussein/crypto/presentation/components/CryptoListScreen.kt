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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.ohoussein.core.designsystem.base.CryptoAppScaffold
import dev.ohoussein.core.designsystem.base.StateError
import dev.ohoussein.core.designsystem.base.StateLoading
import dev.ohoussein.core.designsystem.theme.CryptoAppTheme
import dev.ohoussein.core.formatter.ErrorMessageFormatter
import dev.ohoussein.crypto.presentation.debug.DataPreview.previewListCrypto
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.crypto.presentation.viewmodel.HomeViewModel
import dev.ohoussein.cryptoapp.crypto.presentation.R
import dev.ohoussein.cryptoapp.presentation.model.Resource
import dev.ohoussein.cryptoapp.presentation.model.Status

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
    cryptoListState: Resource<Unit>,
    errorMessageMapper: ErrorMessageFormatter,
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
                    isRefreshing = cryptoListState.status == Status.LOADING
                )
                if (cryptoListState.status == Status.ERROR) {
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        action = {
                            TextButton(onClick = onRefresh) {
                                Text(text = stringResource(id = R.string.core_retry))
                            }
                        }
                    ) {
                        Text(text = errorMessageMapper.map(cryptoListState.error))
                    }
                }
            }
        }
        cryptoListState.status == Status.ERROR -> {
            StateError(
                message = errorMessageMapper.map(cryptoListState.error),
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
    viewModel: HomeViewModel,
    errorMessageMapper: ErrorMessageFormatter,
    onClick: (Crypto) -> Unit,
) {
    val cryptoListState: Resource<Unit> by viewModel.syncState.observeAsState(Resource.loading<Unit>())
    val cryptoList: List<Crypto>? by viewModel.topCryptoList.observeAsState()

    CryptoAppScaffold {
        CryptoListStateScreen(
            cryptoList = cryptoList,
            cryptoListState = cryptoListState,
            errorMessageMapper = errorMessageMapper,
            onClick = onClick,
            onRefresh = { viewModel.refresh(force = true) }
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
