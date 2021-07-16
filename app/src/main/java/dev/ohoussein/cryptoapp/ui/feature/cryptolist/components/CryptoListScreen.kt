package dev.ohoussein.cryptoapp.ui.feature.cryptolist.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.ohoussein.cryptoapp.R
import dev.ohoussein.cryptoapp.debug.DataPreview.previewListCrypto
import dev.ohoussein.cryptoapp.ui.base.StateError
import dev.ohoussein.cryptoapp.ui.base.StateLoading
import dev.ohoussein.cryptoapp.ui.core.mapper.ErrorMessageMapper
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.Resource
import dev.ohoussein.cryptoapp.ui.core.model.Status
import dev.ohoussein.cryptoapp.ui.theme.CryptoAppTheme

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
                        .testTag(CryptoItemTestTag + crypto.id)
                        .fillMaxWidth(),
                    crypto = crypto,
                    onClick = onClick,
                )
            }
        }
    }
}

@Composable
fun CryptoListScreen(
    modifier: Modifier = Modifier,
    cryptoListState: Resource<List<Crypto>>,
    errorMessageMapper: ErrorMessageMapper,
    onClick: (Crypto) -> Unit,
    onRefresh: () -> Unit,
) {

    when {
        cryptoListState.data != null -> {
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                CryptoList(
                    cryptoList = cryptoListState.data,
                    onClick = onClick,
                    onRefresh = onRefresh,
                    isRefreshing = cryptoListState.status == Status.LOADING
                )
                if (cryptoListState.status == Status.ERROR) {
                    Snackbar(
                        modifier = Modifier.padding(8.dp),
                        action = {
                            TextButton(onClick = onRefresh) {
                                Text(text = stringResource(id = R.string.retry))
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
