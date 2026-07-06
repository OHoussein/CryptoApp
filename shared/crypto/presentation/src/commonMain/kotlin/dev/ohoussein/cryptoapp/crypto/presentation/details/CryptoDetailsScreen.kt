package dev.ohoussein.cryptoapp.crypto.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ohoussein.cryptoapp.crypto.presentation.fake.Fake.previewCryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.graph.CryptoPriceGraph
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoDetailsHeader
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoImage
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoLinks
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppTopBar
import dev.ohoussein.cryptoapp.designsystem.base.StateError
import dev.ohoussein.cryptoapp.designsystem.base.StateLoading
import dev.ohoussein.cryptoapp.designsystem.theme.CryptoAppTheme
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

@Composable
fun CryptoDetailsScreen(
    cryptoId: String,
    // This is a workaround while the viewModel builder from koin is ready
    koin: Koin = getKoin(),
    viewModel: CryptoDetailsViewModel = viewModel {
        koin.get { parametersOf(cryptoId) }
    },
    onBackClicked: () -> Unit,
) {
    val state: CryptoDetailsState by viewModel.state.collectAsState()

    CryptoAppScaffold(
        topBar = {
            CryptoAppTopBar(
                title = state.cryptoDetails?.let { "${it.base.name} (${it.base.symbol})" }.orEmpty(),
                titlePrefix = {
                    state.cryptoDetails?.let {
                        CryptoImage(
                            modifier = Modifier.width(42.dp),
                            imageUrl = it.base.imageUrl,
                        )
                    }
                },
                onBackButton = onBackClicked,
            )
        }
    ) { innerPadding ->
        CryptoDetailsStateScreen(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            cryptoDetails = state.cryptoDetails,
            isLoading = state.status == DataStatus.Loading,
            error = (state.status as? DataStatus.Error)?.message,
            onEvent = viewModel::dispatch,
        )
    }
}

@Composable
fun CryptoDetailsStateScreen(
    modifier: Modifier = Modifier,
    cryptoDetails: CryptoDetails?,
    isLoading: Boolean,
    error: String?,
    onEvent: (CryptoDetailsEvents) -> Unit,
) {
    cryptoDetails?.let { data ->
        CryptoDetailsContent(
            modifier = modifier,
            crypto = data,
            onEvent = onEvent,
        )
        return
    }

    when {
        error != null -> StateError(
            modifier = modifier,
            message = error,
            onRetryClick = { onEvent(CryptoDetailsEvents.Refresh) },
        )

        isLoading -> StateLoading(modifier = modifier)
    }
}

@Composable
fun CryptoDetailsContent(
    modifier: Modifier = Modifier,
    crypto: CryptoDetails,
    onEvent: (CryptoDetailsEvents) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier
            .verticalScroll(scrollState)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(vertical = 12.dp),
    ) {
        CryptoPriceGraph(cryptoId = crypto.base.id)
        CryptoDetailsHeader(
            modifier = Modifier.padding(all = 12.dp),
            crypto = crypto,
        )
        CryptoLinks(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 12.dp),
            crypto = crypto,
            onHomePageClicked = { onEvent(CryptoDetailsEvents.HomePageClicked) },
            onBlockchainSiteClicked = { onEvent(CryptoDetailsEvents.BlockchainSiteClicked) },
            onSourceCodeClicked = { onEvent(CryptoDetailsEvents.SourceCodeClicked) },
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}

// The price graph pulls its data from Koin, so this preview renders only the
// header + links which stand on their own.
@Preview
@Composable
private fun PreviewCryptoDetails() {
    CryptoAppTheme {
        CryptoAppScaffold(
            topBar = {
                CryptoAppTopBar(
                    title = with(previewCryptoDetails.base) { "$name ($symbol)" },
                    onBackButton = {},
                )
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 12.dp),
            ) {
                CryptoDetailsHeader(
                    modifier = Modifier.padding(all = 12.dp),
                    crypto = previewCryptoDetails,
                )
                CryptoLinks(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 12.dp),
                    crypto = previewCryptoDetails,
                    onHomePageClicked = {},
                    onBlockchainSiteClicked = {},
                    onSourceCodeClicked = {},
                )
            }
        }
    }
}
