package dev.ohoussein.cryptoapp.data.database.crypto

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoList
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCrypto
import dev.ohoussein.cryptoapp.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.database.CryptoDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CryptoDAOImpl(
    private val database: CryptoDB,
    private val ioDispatcher: CoroutineDispatcher,
    private val dbModelMapper: DBModelMapper,
) : CryptoDAO {

    override suspend fun insert(cryptoList: List<DomainCrypto>) {
        withContext(ioDispatcher) {
            database.cryptoQueries.transaction {
                database.cryptoQueries.deleteAllCrypto()
                cryptoList.forEach { crypto ->
                    val dbCrypto = dbModelMapper.toDB(crypto)
                    database.cryptoQueries.insertCrypto(dbCrypto)
                }
            }
        }
    }

    override fun selectAll(): Flow<CryptoList> {
        return database.cryptoQueries.getAllCrypto(dbModelMapper::toDomainCrypto)
            .asFlow()
            .flowOn(ioDispatcher)
            .mapToList()
            .map(::CryptoList)
    }

    override suspend fun insert(cryptoDetails: DomainCryptoDetails): Unit = withContext(ioDispatcher) {
        val dbCryptoDetails = dbModelMapper.toDB(cryptoDetails)
        database.cryptoQueries.insertCryptoDetails(dbCryptoDetails)
    }

    override fun selectDetails(cryptoDetailsId: String): Flow<DomainCryptoDetails?> {
        return database.cryptoQueries.selectDetails(cryptoDetailsId, dbModelMapper::toDomainCryptoDetails)
            .asFlow()
            .flowOn(ioDispatcher)
            .mapToOneOrNull()
    }
}
