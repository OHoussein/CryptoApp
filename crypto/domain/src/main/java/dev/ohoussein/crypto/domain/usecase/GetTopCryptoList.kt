package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import kotlinx.coroutines.flow.Flow

class GetTopCryptoList(private val cryptoRepository: ICryptoRepository) {

    fun get(cachePolicy: CachePolicy): Flow<CachedData<List<DomainCrypto>>> {
        return cryptoRepository.getTopCryptoList(cachePolicy)
    }
}
