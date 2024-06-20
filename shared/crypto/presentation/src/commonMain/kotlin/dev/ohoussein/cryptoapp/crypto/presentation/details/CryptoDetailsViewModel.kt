package dev.ohoussein.cryptoapp.crypto.presentation.details

import dev.ohoussein.cryptoapp.core.router.Router
import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetCryptoDetailsUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.core.ViewModel
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoDetailsViewModel(
    private val useCase: GetCryptoDetailsUseCase,
    private val modelMapper: DomainModelMapper,
    private val router: Router,
    private val cryptoId: String,
) : ViewModel<CryptoDetailsState, CryptoDetailsEvents>(CryptoDetailsState()) {

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
        }
    }
}
