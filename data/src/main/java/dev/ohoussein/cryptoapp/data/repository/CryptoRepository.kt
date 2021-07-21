package dev.ohoussein.cryptoapp.data.repository

import dev.ohoussein.cryptoapp.data.database.CryptoDatabase
import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
import dev.ohoussein.cryptoapp.data.network.ApiCoinGeckoService
import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CryptoRepository(
    private val service: ApiCoinGeckoService,
    private val database: CryptoDatabase,
    private val mapper: DomainModelMapper,
) : ICryptoRepository {

    override fun getTopCryptoList(vsCurrency: String): Flow<List<DomainCrypto>> {
        return database.cryptoDAO()
            .getAll()
            .filter { it.isNotEmpty() }
            .map { mapper.convertDBCrypto(it) }
    }

    override suspend fun refreshTopCryptoList(vsCurrency: String) {
        val apiResponse = service.getTopCrypto(vsCurrency)
        val domainData = mapper.convert(apiResponse)
        val newDbData = mapper.toDB(domainData)
        database.cryptoDAO().insert(newDbData)
    }

    override fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails> = flow {
        val response = service.getCryptoDetails(cryptoId)
        val domainData = mapper.convert(response)
        emit(domainData)
    }
}
