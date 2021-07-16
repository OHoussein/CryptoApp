package dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val useCase: GetTopCryptoList,
    private val modelMapper: DomainModelMapper
) : ViewModel() {

    private companion object {
        const val VS_CURRENCY = "USD"
    }

    private val _topCryptoList = MutableLiveData<Resource<List<Crypto>>>()

    val topCryptoList: LiveData<Resource<List<Crypto>>>
        get() = _topCryptoList

    init {
        load()
    }

    fun load(refresh: Boolean = false) {
        if (!refresh && topCryptoList.value?.status == Status.SUCCESS)
            return
        val previousData = topCryptoList.value?.data
        viewModelScope.launch {
            useCase(vsCurrency = VS_CURRENCY)
                .map { modelMapper.convert(it, VS_CURRENCY) }
                .asResourceFlow(previousData)
                .flowOn(coroutineContextProvider.io)
                .collect {
                    _topCryptoList.value = it
                }
        }
    }
}
