package dev.ohoussein.cryptoapp.crypto.data.repository

import dev.ohoussein.cryptoapp.crypto.data.mapper.ApiDomainModelMapper
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.crypto.domain.model.HistoricalPrice
import dev.ohoussein.cryptoapp.crypto.domain.model.Locale
import dev.ohoussein.cryptoapp.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.data.cache.CachedDataRepository
import dev.ohoussein.cryptoapp.data.cache.InMemoryCacheDataSource
import dev.ohoussein.cryptoapp.data.database.crypto.CryptoDAO
import dev.ohoussein.cryptoapp.data.network.crypto.service.ApiCryptoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull

class CryptoRepository(
    private val service: ApiCryptoService,
    private val cryptoDao: CryptoDAO,
    private val apiMapper: ApiDomainModelMapper,
    private val locale: Locale,
) : ICryptoRepository {

    private val topCryptoListCache: CachedDataRepository<Unit, List<CryptoModel>> = CachedDataRepository(
        updater = {
            val apiResponse = service.getTopCrypto(locale.currencyCode)
            apiMapper.convert(apiResponse)
        },
        cacheStreamer = {
            cryptoDao.selectAll()
                .filterNot { it.isEmpty() }
        },
        cacheWriter = { _, data ->
            cryptoDao.insert(data)
        },
    )

    private val cryptoDetailsCache: CachedDataRepository<String, CryptoDetailsModel> = CachedDataRepository(
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
        },
    )

    private val historicalPriceCacheDataSource = InMemoryCacheDataSource<HistoricalPriceKey, List<HistoricalPrice>>(
        fetcher = {
            val dto = service.getHistoricalPrices(locale.currencyCode, it.cryptoId, it.days)
            apiMapper.convert(dto)
        }
    )

    override fun getTopCryptoList(): Flow<List<CryptoModel>> = topCryptoListCache.stream(Unit)

    override suspend fun refreshTopCryptoList() = topCryptoListCache.refresh(Unit)

    override suspend fun refreshCryptoDetails(cryptoId: String) = cryptoDetailsCache.refresh(cryptoId)

    override fun getCryptoDetails(cryptoId: String): Flow<CryptoDetailsModel> = cryptoDetailsCache.stream(cryptoId)

    override suspend fun getHistoricalPrices(cryptoId: String, days: Int): Result<List<HistoricalPrice>> {
        return runCatching { historicalPriceCacheDataSource.read(HistoricalPriceKey(cryptoId, days)) }
    }
}

internal data class HistoricalPriceKey(val cryptoId: String, val days: Int)
