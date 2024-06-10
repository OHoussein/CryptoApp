package dev.ohoussein.cryptoapp.crypto.presentation.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoDetailsHeader
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoImage
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoLinks
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppTopBar
import dev.ohoussein.cryptoapp.designsystem.base.StateError
import dev.ohoussein.cryptoapp.designsystem.base.StateLoading
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

    LaunchedEffect(Unit) {
        viewModel.dispatch(CryptoDetailsEvents.Refresh)
    }

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
    ) {
        CryptoDetailsStateScreen(
            Modifier.fillMaxSize(),
            cryptoDetails = state.cryptoDetails,
            isLoading = state.status == DataStatus.Loading,
            error = (state.status as? DataStatus.Error)?.message,
            onRefresh = { viewModel.dispatch(CryptoDetailsEvents.Refresh) },
            onHomePageClicked = {
                viewModel.dispatch(CryptoDetailsEvents.HomePageClicked)
            },
            onBlockchainSiteClicked = {
                viewModel.dispatch(CryptoDetailsEvents.BlockchainSiteClicked)
            },
            onSourceCodeClicked = {
                viewModel.dispatch(CryptoDetailsEvents.SourceCodeClicked)
            },
        )
    }
}

@Composable
fun CryptoDetailsStateScreen(
    modifier: Modifier = Modifier,
    cryptoDetails: CryptoDetails?,
    isLoading: Boolean,
    error: String?,
    onRefresh: () -> Unit,
    onHomePageClicked: (CryptoDetails) -> Unit,
    onBlockchainSiteClicked: (CryptoDetails) -> Unit,
    onSourceCodeClicked: (CryptoDetails) -> Unit,
) {
    cryptoDetails?.let { data ->
        CryptoDetailsContent(
            modifier = modifier,
            crypto = data,
            onHomePageClicked = onHomePageClicked,
            onBlockchainSiteClicked = onBlockchainSiteClicked,
            onSourceCodeClicked = onSourceCodeClicked,
        )
        return
    }

    when {
        error != null -> StateError(
            modifier = modifier,
            message = error,
            onRetryClick = onRefresh,
        )

        isLoading -> StateLoading(modifier = modifier)
    }
}

@Composable
fun CryptoDetailsContent(
    modifier: Modifier = Modifier,
    crypto: CryptoDetails,
    onHomePageClicked: (CryptoDetails) -> Unit,
    onBlockchainSiteClicked: (CryptoDetails) -> Unit,
    onSourceCodeClicked: (CryptoDetails) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState),
    ) {
        CryptoDetailsHeader(
            Modifier.padding(top = 12.dp),
            crypto = crypto,
        )
        CryptoLinks(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            crypto = crypto,
            onHomePageClicked = onHomePageClicked,
            onBlockchainSiteClicked = onBlockchainSiteClicked,
            onSourceCodeClicked = onSourceCodeClicked,
        )
        Spacer(modifier = Modifier.size(24.dp))
    }
}
