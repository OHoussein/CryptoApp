package dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.cryptoapp.common.coroutine.CoroutineContextProvider
import dev.ohoussein.cryptoapp.domain.usecase.GetCryptoDetails
import dev.ohoussein.cryptoapp.ui.core.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.ui.core.model.CryptoDetails
import dev.ohoussein.cryptoapp.ui.core.model.Resource
import dev.ohoussein.cryptoapp.ui.core.util.asResourceFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val useCase: GetCryptoDetails,
    private val modelMapper: DomainModelMapper
) : ViewModel() {

    private val _cryptoId = MutableLiveData<String>()

    val cryptoDetails: LiveData<Resource<CryptoDetails>> = _cryptoId.switchMap { id ->
        useCase(id)
            .map { modelMapper.convert(it) }
            .asResourceFlow()
            .flowOn(coroutineContextProvider.io)
            .asLiveData()
    }

    fun load(cryptoId: String) {
        _cryptoId.value = cryptoId
    }
}