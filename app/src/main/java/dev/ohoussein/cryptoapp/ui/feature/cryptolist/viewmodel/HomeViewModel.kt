package dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.cryptoapp.common.coroutine.CoroutineContextProvider
import dev.ohoussein.cryptoapp.domain.usecase.GetTopCryptoList
import dev.ohoussein.cryptoapp.ui.core.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.ui.core.model.Crypto
import dev.ohoussein.cryptoapp.ui.core.model.Resource
import dev.ohoussein.cryptoapp.ui.core.model.Status
import dev.ohoussein.cryptoapp.ui.core.util.asResourceFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    coroutineContextProvider: CoroutineContextProvider,
    private val useCase: GetTopCryptoList,
    private val modelMapper: DomainModelMapper
) : ViewModel() {

    private companion object {
        const val VS_CURRENCY = "USD"
    }

    val topCryptoList: LiveData<List<Crypto>> =
        useCase.get(VS_CURRENCY)
            .map { modelMapper.convert(it, VS_CURRENCY) }
            .flowOn(coroutineContextProvider.io)
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
