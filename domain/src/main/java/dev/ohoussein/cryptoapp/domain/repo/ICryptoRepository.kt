package dev.ohoussein.cryptoapp.domain.repo

import dev.ohoussein.cryptoapp.domain.model.DomainCrypto
import kotlinx.coroutines.flow.Flow

interface ICryptoRepository {

    fun getTopCryptoList(vsCurrency: String): Flow<List<DomainCrypto>>
}
