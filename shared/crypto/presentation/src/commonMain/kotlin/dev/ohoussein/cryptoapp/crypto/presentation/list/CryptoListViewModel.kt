package dev.ohoussein.cryptoapp.crypto.presentation.list

import dev.ohoussein.cryptoapp.crypto.domain.usecase.GetTopCryptoListUseCase
import dev.ohoussein.cryptoapp.crypto.presentation.core.ViewModel
import dev.ohoussein.cryptoapp.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.crypto.presentation.model.DataStatus
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoListViewModel(
    private val useCase: GetTopCryptoListUseCase,
    private val modelMapper: DomainModelMapper,
) : ViewModel<CryptoListState, CryptoListEvents>(CryptoListState()) {

    init {
        useCase.observe()
            .map(modelMapper::convert)
            .onEach { data ->
                mutableState.update { it.copy(cryptoList = data) }
            }
            .launchIn(viewModelScope)
        refresh()
    }

    override fun dispatch(event: CryptoListEvents) {
        when (event) {
            CryptoListEvents.Refresh -> refresh()
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            mutableState.update { it.copy(status = DataStatus.Loading) }
            runCatching {
                useCase.refresh()
            }.onSuccess {
                mutableState.update { it.copy(status = DataStatus.Success) }
            }.onFailure { error ->
                mutableState.update { it.copy(status = DataStatus.Error(error.message ?: "Error")) }
            }
        }
    }
}
