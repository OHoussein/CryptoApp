package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.common.resource.Resource
import dev.ohoussein.cryptoapp.common.resource.asCachedResourceFlow
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val useCase: GetCryptoDetails,
    private val modelMapper: DomainModelMapper,
    private val errorMessageFormatter: ErrorMessageFormatter,
) : ViewModel() {

    private val _cryptoId = MutableLiveData<String>()

    val cryptoDetails: LiveData<Resource<CryptoDetails>> = _cryptoId.switchMap { id ->
        useCase(id, CachePolicy.CACHE_THEN_FRESH)
            .map { it.map(modelMapper.convert(it.data)) }
            .asCachedResourceFlow(errorMessageFormatter::map)
            .asLiveData()
    }

    fun load(cryptoId: String) {
        _cryptoId.value = cryptoId
    }
}
