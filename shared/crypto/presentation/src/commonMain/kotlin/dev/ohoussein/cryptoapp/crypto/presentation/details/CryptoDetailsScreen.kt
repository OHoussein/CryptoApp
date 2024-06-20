package dev.ohoussein.cryptoapp.crypto.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoDetailsHeader
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoImage
import dev.ohoussein.cryptoapp.crypto.presentation.uicomponents.CryptoLinks
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppScaffold
import dev.ohoussein.cryptoapp.designsystem.base.CryptoAppTopBar
import dev.ohoussein.cryptoapp.designsystem.base.StateError
import dev.ohoussein.cryptoapp.designsystem.base.StateLoading
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import dev.ohoussein.cryptoapp.designsystem.graph.ui.LinearGraph
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
    ) {
        CryptoDetailsStateScreen(
            Modifier.fillMaxSize(),
            cryptoDetails = state.cryptoDetails,
            graphPrices = state.graphState.graphPrices,
            isLoading = state.status == DataStatus.Loading,
            error = (state.status as? DataStatus.Error)?.message,
            graphState = state.graphState,
            onEvent = viewModel::dispatch,
        )
    }
}

@Composable
fun CryptoDetailsStateScreen(
    modifier: Modifier = Modifier,
    cryptoDetails: CryptoDetails?,
    graphPrices: List<GraphPoint>,
    graphState: GraphState,
    isLoading: Boolean,
    error: String?,
    onEvent: (CryptoDetailsEvents) -> Unit,
) {
    cryptoDetails?.let { data ->
        CryptoDetailsContent(
            modifier = modifier,
            crypto = data,
            graphPrices = graphPrices,
            graphState = graphState,
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
    graphPrices: List<GraphPoint>,
    graphState: GraphState,
    onEvent: (CryptoDetailsEvents) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier
            .verticalScroll(scrollState)
            .padding(vertical = 12.dp),
    ) {
        GraphPrices(
            prices = graphPrices,
            graphState = graphState,
            onSelectInterval = { onEvent(CryptoDetailsEvents.SelectInterval(it)) },
            modifier = Modifier,
        )
        CryptoDetailsHeader(
            modifier = Modifier.padding(all = 12.dp),
            crypto = crypto,
            onLinkClick = { onEvent(CryptoDetailsEvents.LinkClicked(it)) },
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

@Composable
private fun GraphPrices(
    prices: List<GraphPoint>,
    graphState: GraphState,
    onSelectInterval: (GraphInterval) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LinearGraph(
            values = prices,
            color = MaterialTheme.colors.primaryVariant,
            stroke = 2.dp,
            gridColor = Color.LightGray.copy(alpha = 0.5f),
            gridTextStyle = MaterialTheme.typography.caption,
            horizontalGridPoints = graphState.horizontalGridPoints,
            verticalGridPoints = graphState.verticalGridPoints,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
        )

        GraphIntervals(
            modifier = Modifier.padding(horizontal = 12.dp),
            allIntervals = graphState.allIntervals,
            selectedInterval = graphState.selectedInterval,
            onSelectInterval = onSelectInterval,
        )
    }
}

@Composable
private fun GraphIntervals(
    allIntervals: List<GraphInterval>,
    selectedInterval: GraphInterval,
    onSelectInterval: (GraphInterval) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        allIntervals.forEach { interval ->
            val isSelected = selectedInterval == interval
            Text(
                text = interval.asString,
                color = if (isSelected) MaterialTheme.colors.primaryVariant else Color.Unspecified,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable { onSelectInterval(interval) }

            )
        }
    }
}
