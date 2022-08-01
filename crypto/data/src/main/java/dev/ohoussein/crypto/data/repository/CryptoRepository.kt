package dev.ohoussein.crypto.data.repository

import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.core.Qualifier
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class CryptoRepository @Inject constructor(
    private val service: ApiCryptoService,
    private val cryptoDao: CryptoDAO,
    private val apiMapper: ApiDomainModelMapper,
    private val dbMapper: DbDomainModelMapper,
    @Named(Qualifier.CURRENCY) private val currency: String,
) : ICryptoRepository {

    override fun getTopCryptoList(): Flow<List<DomainCrypto>> {
        return cryptoDao
            .getAll()
            .filter { it.isNotEmpty() }
            .map { dbMapper.convertDBCrypto(it) }
    }

    override suspend fun refreshTopCryptoList() {
        val apiResponse = service.getTopCrypto(currency)
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
