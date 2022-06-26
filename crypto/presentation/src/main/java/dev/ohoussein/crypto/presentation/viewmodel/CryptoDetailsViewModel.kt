package dev.ohoussein.crypto.presentation.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ohoussein.crypto.domain.usecase.GetCryptoDetails
import dev.ohoussein.crypto.presentation.mapper.DomainModelMapper
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.cryptoapp.presentation.model.Resource
import dev.ohoussein.cryptoapp.presentation.util.asResourceFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(
    private val useCase: GetCryptoDetails,
    private val modelMapper: DomainModelMapper
) : ViewModel() {

    private val _cryptoId = MutableLiveData<String>()

    val cryptoDetails: LiveData<Resource<CryptoDetails>> = _cryptoId.switchMap { id ->
        useCase(id)
            .map { modelMapper.convert(it) }
            .asResourceFlow()
            .asLiveData()
    }

    fun load(cryptoId: String) {
        _cryptoId.value = cryptoId
    }
}