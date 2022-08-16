package dev.ohoussein.crypto.domain.usecase

import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.cacheddata.CachePolicy
import dev.ohoussein.cryptoapp.cacheddata.CachedData
import kotlinx.coroutines.flow.Flow

class GetCryptoDetails(private val repository: ICryptoRepository) {

    operator fun invoke(cryptoId: String, cachePolicy: CachePolicy): Flow<CachedData<DomainCryptoDetails>> {
        return repository.getCryptoDetails(cryptoId, cachePolicy)
    }
}
