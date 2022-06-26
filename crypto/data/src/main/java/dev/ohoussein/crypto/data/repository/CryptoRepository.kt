package dev.ohoussein.crypto.data.repository

import dev.ohoussein.crypto.data.api.ApiCoinGeckoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.data.database.CryptoDAO
import dev.ohoussein.crypto.data.database.mapper.DbDomainModelMapper
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CryptoRepository(
        private val service: ApiCoinGeckoService,
        private val cryptoDao: CryptoDAO,
        private val apiMapper: ApiDomainModelMapper,
        private val dbMapper: DbDomainModelMapper,
) : ICryptoRepository {

    override fun getTopCryptoList(vsCurrency: String): Flow<List<DomainCrypto>> {
        return cryptoDao
                .getAll()
                .filter { it.isNotEmpty() }
                .map { dbMapper.convertDBCrypto(it) }
    }

    override suspend fun refreshTopCryptoList(vsCurrency: String) {
        val apiResponse = service.getTopCrypto(vsCurrency)
        val domainData = apiMapper.convert(apiResponse)
        val newDbData = dbMapper.toDB(domainData)
        cryptoDao.insert(newDbData)
    }

    override fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails> = flow {
        val response = service.getCryptoDetails(cryptoId)
        val domainData = apiMapper.convert(response)
        emit(domainData)
    }
}
