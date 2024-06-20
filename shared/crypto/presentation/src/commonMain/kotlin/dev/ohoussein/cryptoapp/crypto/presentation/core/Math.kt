package dev.ohoussein.cryptoapp.crypto.presentation.core

fun List<Double>.averageValues(maxValues: Int = 20): List<Double> {
    if (size <= maxValues) {
        return this
    }

    val groupSize = size / maxValues
    val averagedPrices = mutableListOf<Double>()

    for (i in indices step groupSize) {
        val group = subList(i, minOf(i + groupSize, size))
        val avgPrice = group.average()
        averagedPrices.add(avgPrice)
    }

    return averagedPrices
}

fun interpolateValues(
    start: Long,
    end: Long,
    countValues: Int,
): List<Long> {
    val stepSize = (end - start).toDouble() / (countValues - 1)
    return (0 until countValues).map {
        start + (it * stepSize).toLong()
    }
}

fun interpolateValues(
    start: Double,
    end: Double,
    countValues: Int,
): List<Double> {
    val stepSize = (end - start) / (countValues - 1)
    return (0 until countValues).map {
        start + (it * stepSize)
    }
}
