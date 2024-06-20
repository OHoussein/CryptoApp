package dev.ohoussein.cryptoapp.crypto.presentation.graph

import androidx.compose.runtime.Composable
import cryptoapp.shared.crypto.presentation.generated.resources.*
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import dev.ohoussein.cryptoapp.designsystem.graph.model.GridPoint
import org.jetbrains.compose.resources.stringResource

data class CryptoPriceGraphState(
    val graphPrices: List<GraphPoint> = emptyList(),
    val selectedInterval: GraphInterval = GraphInterval.INTERVAL_7_DAYS,
    val allIntervals: List<GraphInterval> = GraphInterval.entries,
    val horizontalGridPoints: List<GridPoint> = emptyList(),
    val verticalGridPoints: List<GridPoint> = emptyList(),
)

val GraphInterval.asString
    @Composable
    get() = stringResource(
        when (this) {
            GraphInterval.INTERVAL_1_DAY -> Res.string.interval_1_day
            GraphInterval.INTERVAL_7_DAYS -> Res.string.interval_7_days
            GraphInterval.INTERVAL_1_MONTH -> Res.string.interval_1_month
            GraphInterval.INTERVAL_3_MONTHS -> Res.string.interval_3_months
            GraphInterval.INTERVAL_1_YEAR -> Res.string.interval_1_year
        }
    )
