package dev.ohoussein.cryptoapp.data.database.crypto

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoDetailsModel
import dev.ohoussein.cryptoapp.crypto.domain.model.CryptoModel
import dev.ohoussein.cryptoapp.database.CryptoDB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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

    override fun selectAll(): Flow<List<CryptoModel>> {
        return database.cryptoQueries.getAllCrypto(dbModelMapper::toCryptoModel)
            .asFlow()
            .mapToList(ioDispatcher)
    }

    override suspend fun insert(cryptoDetails: CryptoDetailsModel): Unit = withContext(ioDispatcher) {
        val dbCryptoDetails = dbModelMapper.toDB(cryptoDetails)
        database.cryptoQueries.insertCryptoDetails(dbCryptoDetails)
    }

    override fun selectDetails(cryptoDetailsId: String): Flow<CryptoDetailsModel?> {
        return database.cryptoQueries.selectDetails(cryptoDetailsId, dbModelMapper::toCryptoDetailsModel)
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
    }
}
