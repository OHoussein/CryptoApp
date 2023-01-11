package dev.ohoussein.cryptoapp.data.database.crypto

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoListModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
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

    override suspend fun insert(cryptoList: List<CryptoModel>) {
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

    override fun selectAll(): Flow<CryptoListModel> {
        return database.cryptoQueries.getAllCrypto(dbModelMapper::toCryptoModel)
            .asFlow()
            .flowOn(ioDispatcher)
            .mapToList()
            .map(::CryptoListModel)
    }

    override suspend fun insert(cryptoDetails: CryptoDetailsModel): Unit = withContext(ioDispatcher) {
        val dbCryptoDetails = dbModelMapper.toDB(cryptoDetails)
        database.cryptoQueries.insertCryptoDetails(dbCryptoDetails)
    }

    override fun selectDetails(cryptoDetailsId: String): Flow<CryptoDetailsModel?> {
        return database.cryptoQueries.selectDetails(cryptoDetailsId, dbModelMapper::toCryptoDetailsModel)
            .asFlow()
            .flowOn(ioDispatcher)
            .mapToOneOrNull()
    }
}
