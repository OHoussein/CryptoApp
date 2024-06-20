package dev.ohoussein.cryptoapp.crypto.presentation.graph

import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.core.ViewModel
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoPriceGraphViewModel(
    private val useCase: GetCryptoDetailsUseCase,
    private val modelMapper: DomainModelMapper,
    private val graphGridGenerator: GraphGridGenerator,
    private val cryptoId: String,
) : ViewModel<CryptoPriceGraphState, CryptoPriceGraphEvents>(CryptoPriceGraphState()) {

    private val historicalPricesCachedData = mutableMapOf<Int, List<HistoricalPrice>>()

    init {
        loadHistoricalPrices(state.value.selectedInterval)
    }

    override fun dispatch(event: CryptoPriceGraphEvents) {
        when (event) {
            is CryptoPriceGraphEvents.SelectInterval -> loadHistoricalPrices(event.interval)
        }
    }

    private fun loadHistoricalPrices(interval: GraphInterval, forceRefresh: Boolean = false) = viewModelScope.launch {
        mutableState.update {
            it.copy(selectedInterval = interval)
        }
        val intervalInDays = interval.countDays
        if (forceRefresh) {
            historicalPricesCachedData.clear()
        }
        historicalPricesCachedData[intervalInDays]?.let { prices ->
            val graphPoints = modelMapper.convertHistoricalPrices(prices)
            selectHistoricalValues(graphPoints, prices)
            return@launch
        }
        useCase.getHistoricalPrices(cryptoId, intervalInDays)
            .onSuccess { prices ->
                val graphPoints = modelMapper.convertHistoricalPrices(prices)
                historicalPricesCachedData[intervalInDays] = prices
                selectHistoricalValues(graphPoints, prices)
            }
            .onFailure { error ->
                println(error)
            }
    }

    private fun selectHistoricalValues(graphPoints: List<GraphPoint>, prices: List<HistoricalPrice>) {
        val horizontalGrid = graphGridGenerator.getPriceGridInstants(
            prices = prices,
            countValues = 5,
        )
        val verticalGrid = graphGridGenerator.getTimeGridInstants(
            prices,
            countValues = 5,
            timeInterval = state.value.selectedInterval,
        )
        mutableState.update {
            it.copy(
                graphPrices = graphPoints,
                horizontalGridPoints = horizontalGrid,
                verticalGridPoints = verticalGrid,
            )
        }
    }
}
