package dev.ohoussein.cryptoapp.crypto.presentation.graph

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import dev.ohoussein.cryptoapp.designsystem.graph.ui.LinearGraph
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf

@Composable
fun CryptoPriceGraph(
    cryptoId: String,
    modifier: Modifier = Modifier,
    koin: Koin = getKoin(),
    viewModel: CryptoPriceGraphViewModel = viewModel {
        koin.get { parametersOf(cryptoId) }
    },
) {
    val state: CryptoPriceGraphState by viewModel.state.collectAsState()

    CryptoPriceGraphContent(
        modifier = modifier,
        graphState = state,
        onSelectInterval = { viewModel.dispatch(CryptoPriceGraphEvents.SelectInterval(it)) },
    )
}

@Composable
private fun CryptoPriceGraphContent(
    graphState: CryptoPriceGraphState,
    onSelectInterval: (GraphInterval) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LinearGraph(
            values = graphState.graphPrices,
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
