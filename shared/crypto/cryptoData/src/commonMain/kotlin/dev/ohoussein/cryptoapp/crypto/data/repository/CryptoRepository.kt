package dev.ohoussein.cryptoapp.crypto.data.repository

import dev.ohoussein.cryptoapp.crypto.data.mapper.ApiDomainModelMapper
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.data.cache.CachedDataRepository
import dev.ohoussein.cryptoapp.data.database.crypto.CryptoDAO
import dev.ohoussein.cryptoapp.data.network.crypto.service.ApiCryptoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull

class CryptoRepository constructor(
    private val service: ApiCryptoService,
    private val cryptoDao: CryptoDAO,
    private val apiMapper: ApiDomainModelMapper,
    private val locale: Locale,
) : ICryptoRepository {

    private val topCryptoListCache: CachedDataRepository<Unit, CryptoList> = CachedDataRepository(
        updater = {
            val apiResponse = service.getTopCrypto(locale.currencyCode)
            apiMapper.convert(apiResponse)
        },
        cacheStreamer = {
            cryptoDao.selectAll()
                .filterNot { it.isEmpty }
        },
        cacheWriter = { _, data ->
            cryptoDao.insert(data.list)
        },
    )

    private val cryptoDetailsCache: CachedDataRepository<String, DomainCryptoDetails> = CachedDataRepository(
        updater = { id ->
            val response = service.getCryptoDetails(id)
            apiMapper.convert(response)
        },
        cacheStreamer = { id ->
            cryptoDao.selectDetails(id)
                .filterNotNull()
        },
        cacheWriter = { _, data ->
            cryptoDao.insert(data)
            println("Wiss: write Details $data")
        },
    )

    override fun getTopCryptoList(): Flow<CryptoList> = topCryptoListCache.stream(Unit)

    override suspend fun refreshTopCryptoList() = topCryptoListCache.refresh(Unit)

    override suspend fun refreshCryptoDetails(cryptoId: String) = cryptoDetailsCache.refresh(cryptoId)

    override fun getCryptoDetails(cryptoId: String): Flow<DomainCryptoDetails> = cryptoDetailsCache.stream(cryptoId)
}
