package dev.ohoussein.cryptoapp.data.repository

import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.data.network.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteCryptoRepository(
    private val service: ApiCoinGeckoService,
    private val mapper: DomainModelMapper
) : ICryptoRepository {

    override fun getTopCryptoList(vsCurrency: String): Flow<List<DomainCrypto>> = flow {
        val response = service.getTopCrypto(vsCurrency)
        val domainData = mapper.convert(response)
        emit(domainData)
    }

    override fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails> = flow {
        val response = service.getCryptoDetails(cryptoId)
        val domainData = mapper.convert(response)
        emit(domainData)
    }
}
