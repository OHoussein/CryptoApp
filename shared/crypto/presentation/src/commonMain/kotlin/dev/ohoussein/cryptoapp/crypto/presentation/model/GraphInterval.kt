package dev.ohoussein.cryptoapp.crypto.presentation.model

@Suppress("MagicNumber")
enum class GraphInterval(val countDays: Int) {
    INTERVAL_1_DAY(1),
    INTERVAL_7_DAYS(7),
    INTERVAL_1_MONTH(30),
    INTERVAL_3_MONTHS(30 * 3),
    INTERVAL_1_YEAR(30 * 12),
}
