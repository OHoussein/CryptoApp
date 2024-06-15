package dev.ohoussein.cryptoapp.crypto.presentation.details

import androidx.compose.runtime.Composable
import cryptoapp.shared.crypto.presentation.generated.resources.*
import cryptoapp.shared.crypto.presentation.generated.resources.Res
import cryptoapp.shared.crypto.presentation.generated.resources.interval_1_day
import cryptoapp.shared.crypto.presentation.generated.resources.interval_1_month
import cryptoapp.shared.crypto.presentation.generated.resources.interval_7_days
import dev.ohoussein.cryptoapp.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import org.jetbrains.compose.resources.stringResource

data class CryptoDetailsState(
    val cryptoDetails: CryptoDetails? = null,
    val status: DataStatus = DataStatus.Loading,
    val graphPrices: List<GraphPoint> = emptyList(),
    val selectedInterval: Interval = Interval.INTERVAL_7_DAYS,
    val allIntervals: List<Interval> = Interval.entries,
)

@Suppress("MagicNumber")
enum class Interval(val countDays: Int) {
    INTERVAL_1_DAY(1),
    INTERVAL_7_DAYS(7),
    INTERVAL_1_MONTH(30),
    INTERVAL_3_MONTHS(30 * 3),
    INTERVAL_1_YEAR(30 * 12),
}

val Interval.asString
    @Composable
    get() = stringResource(
        when (this) {
            Interval.INTERVAL_1_DAY -> Res.string.interval_1_day
            Interval.INTERVAL_7_DAYS -> Res.string.interval_7_days
            Interval.INTERVAL_1_MONTH -> Res.string.interval_1_month
            Interval.INTERVAL_3_MONTHS -> Res.string.interval_3_months
            Interval.INTERVAL_1_YEAR -> Res.string.interval_1_year
        }
    )
