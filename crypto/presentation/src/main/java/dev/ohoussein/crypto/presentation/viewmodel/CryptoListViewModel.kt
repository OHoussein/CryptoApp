package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.crypto.domain.usecase.GetTopCryptoList
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.common.resource.Resource
import dev.ohoussein.cryptoapp.common.resource.asCachedResourceFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val useCase: GetTopCryptoList,
    private val modelMapper: DomainModelMapper,
) : ViewModel() {

    private val _topCryptoList = MutableLiveData<Resource<List<Crypto>>>()
    val topCryptoList: LiveData<Resource<List<Crypto>>>
        get() = _topCryptoList

    fun onScreenOpened() {
        if (topCryptoList.value != null)
            return
        useCase.get(CachePolicy.CACHE_THEN_FRESH)
            .map { it.map(modelMapper.convert(it.data)) }
            .asCachedResourceFlow(_topCryptoList.value?.data)
            .onEach {
                _topCryptoList.value = it
            }
            .launchIn(viewModelScope)
    }

    fun onRefresh() {
        useCase.get(CachePolicy.FRESH)
            .map { it.map(modelMapper.convert(it.data)) }
            .asCachedResourceFlow(_topCryptoList.value?.data)
            .onEach {
                _topCryptoList.value = it
            }
            .launchIn(viewModelScope)
    }
}
