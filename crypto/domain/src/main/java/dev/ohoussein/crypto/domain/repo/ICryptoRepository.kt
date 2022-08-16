package dev.ohoussein.crypto.domain.repo

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(cachePolicy: CachePolicy): Flow<CachedData<List<DomainCrypto>>>

    fun getCryptoDetails(cryptoId: String, cachePolicy: CachePolicy): Flow<CachedData<DomainCryptoDetails>>
}
