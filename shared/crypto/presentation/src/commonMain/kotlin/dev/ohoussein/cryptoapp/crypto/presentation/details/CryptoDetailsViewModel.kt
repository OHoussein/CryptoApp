package dev.ohoussein.cryptoapp.crypto.presentation.details

import dev.ohoussein.cryptoapp.core.router.Router
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.core.ViewModel
import dev.ohoussein.cryptoapp.crypto.presentation.graph.GraphGridGenerator
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import dev.ohoussein.cryptoapp.crypto.presentation.model.GraphInterval
import dev.ohoussein.cryptoapp.designsystem.graph.model.GraphPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoDetailsViewModel(
    private val useCase: GetCryptoDetailsUseCase,
    private val modelMapper: DomainModelMapper,
    private val graphGridGenerator: GraphGridGenerator,
    private val router: Router,
    private val cryptoId: String,
) : ViewModel<CryptoDetailsState, CryptoDetailsEvents>(CryptoDetailsState()) {

    private val historicalPricesCachedData = mutableMapOf<Int, List<HistoricalPrice>>()

    init {
        useCase.observe(cryptoId)
            .map(modelMapper::convert)
            .onEach { cryptoDetails ->
                mutableState.update { it.copy(cryptoDetails = cryptoDetails) }
            }
            .launchIn(viewModelScope)

        refresh()
    }

    override fun dispatch(event: CryptoDetailsEvents) {
        when (event) {
            CryptoDetailsEvents.Refresh -> refresh()

            CryptoDetailsEvents.BlockchainSiteClicked ->
                state.value.cryptoDetails?.blockchainSite?.let { router.openUrl(it) }

            CryptoDetailsEvents.HomePageClicked ->
                state.value.cryptoDetails?.homePageUrl?.let { router.openUrl(it) }

            CryptoDetailsEvents.SourceCodeClicked ->
                state.value.cryptoDetails?.mainRepoUrl?.let { router.openUrl(it) }

            is CryptoDetailsEvents.LinkClicked -> router.openUrl(event.url)

            is CryptoDetailsEvents.SelectInterval -> loadHistoricalPrices(event.interval)
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            mutableState.update { it.copy(status = DataStatus.Loading) }
            runCatching {
                useCase.refresh(cryptoId)
            }.onSuccess {
                mutableState.update { it.copy(status = DataStatus.Success) }
            }.onFailure { error ->
                mutableState.update { it.copy(status = DataStatus.Error(error.message ?: "Error")) }
            }
            loadHistoricalPrices(state.value.graphState.selectedInterval, forceRefresh = true)
        }
    }

    private fun loadHistoricalPrices(interval: GraphInterval, forceRefresh: Boolean = false) = viewModelScope.launch {
        mutableState.update {
            it.copy(graphState = it.graphState.copy(selectedInterval = interval))
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
                mutableState.update { it.copy(status = DataStatus.Error(error.message ?: "Error")) }
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
            timeInterval = state.value.graphState.selectedInterval
        )
        mutableState.update {
            val graphState = it.graphState.copy(
                graphPrices = graphPoints,
                horizontalGridPoints = horizontalGrid,
                verticalGridPoints = verticalGrid,
            )
            it.copy(graphState = graphState)
        }
    }
}
