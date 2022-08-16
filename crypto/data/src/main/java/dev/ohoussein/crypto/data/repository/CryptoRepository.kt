package dev.ohoussein.crypto.data.repository

import dev.ohoussein.crypto.data.api.ApiCryptoService
import dev.ohoussein.crypto.data.api.mapper.ApiDomainModelMapper
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import dev.ohoussein.cryptoapp.core.Qualifier
import dev.ohoussein.cryptoapp.data.cache.Cache
import dev.ohoussein.cryptoapp.data.cache.CachedDataRepository
import dev.ohoussein.cryptoapp.data.database.crypto.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.database.crypto.mapper.DbDomainModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

class CryptoRepository @Inject constructor(
    private val service: ApiCryptoService,
    private val cryptoDao: CryptoDAO,
    private val apiMapper: ApiDomainModelMapper,
    private val dbMapper: DbDomainModelMapper,
    @Named(Qualifier.CURRENCY) private val currency: String,
) : ICryptoRepository {

    private val topCryptoList: CachedDataRepository<Unit, List<DomainCrypto>> = CachedDataRepository.create(
        dataFetcher = {
            val apiResponse = service.getTopCrypto(currency)
            apiMapper.convert(apiResponse)
        },
        cache = Cache.of(
            reader = {
                cryptoDao.getAll()
                    .onEach {
                        println("Wiss: onEach getAll ${it.size}")
                    }
                    .map(dbMapper::convertDBCrypto)
            },
            writer = { _: Unit, data: List<DomainCrypto> ->
                val newDbData = dbMapper.toDB(data)
                cryptoDao.replace(newDbData)
            }
        )
    )

    private val cryptoDetails: CachedDataRepository<String, DomainCryptoDetails> = CachedDataRepository.create(
        dataFetcher = { id ->
            val response = service.getCryptoDetails(id)
            apiMapper.convert(response)
        },
        cache = Cache.of(
            reader = { id ->
                cryptoDao.getCryptoDetails(id)
                    .map {
                        if (it != null)
                            dbMapper.toDomain(it)
                        else
                            null
                    }
            },
            writer = { _: String, data: DomainCryptoDetails ->
                val newDbData = dbMapper.toDB(data)
                cryptoDao.insert(newDbData)
            }
        )
    )

    override fun getTopCryptoList(cachePolicy: CachePolicy): Flow<CachedData<List<DomainCrypto>>> {
        return topCryptoList.stream(Unit, cachePolicy)
    }

    override fun getCryptoDetails(cryptoId: String, cachePolicy: CachePolicy): Flow<CachedData<DomainCryptoDetails>> {
        return cryptoDetails.stream(cryptoId, cachePolicy)
    }
}
