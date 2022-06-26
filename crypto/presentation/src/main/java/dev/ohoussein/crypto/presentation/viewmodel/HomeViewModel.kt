package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.presentation.model.Resource
import dev.ohoussein.cryptoapp.presentation.model.Status
import dev.ohoussein.cryptoapp.presentation.util.asResourceFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
        private val useCase: GetTopCryptoList,
        private val modelMapper: DomainModelMapper
) : ViewModel() {

    private companion object {
        const val VS_CURRENCY = "USD"
    }

    val topCryptoList: LiveData<List<dev.ohoussein.crypto.presentation.model.Crypto>> =
            useCase.get(VS_CURRENCY)
                    .map { modelMapper.convert(it, VS_CURRENCY) }
                    .asLiveData()

    private val _syncState = MutableLiveData<Resource<Unit>>()
    val syncState: LiveData<Resource<Unit>>
        get() = _syncState

    init {
        refresh()
    }

    fun refresh(force: Boolean = false) {
        if (!force && _syncState.value?.status == Status.SUCCESS)
            return
        viewModelScope.launch {
            asResourceFlow { useCase.refresh(VS_CURRENCY) }
                .collect {
                    Timber.d("Sync state ${it.status}")
                    _syncState.value = it
                }
        }
    }
}
