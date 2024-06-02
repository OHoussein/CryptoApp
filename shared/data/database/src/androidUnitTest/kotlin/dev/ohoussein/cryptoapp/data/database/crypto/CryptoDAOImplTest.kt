package dev.ohoussein.cryptoapp.data.database.crypto

import dev.ohoussein.cryptoapp.data.database.createDatabase
import dev.ohoussein.cryptoapp.data.database.crypto.mock.TestDataFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class CryptoDAOImplTest {

    lateinit var cryptoDAO: CryptoDAO

    @Before
    fun setup() {
        val database = createDatabase()
        cryptoDAO = CryptoDAOImpl(
            database = database,
            ioDispatcher = UnconfinedTestDispatcher(),
            dbModelMapper = DBModelMapper(),
        )
    }

    @Test
    fun should_insert_and_get_crypto_list() = runTest {
        // Given
        val dbData = TestDataFactory.makeCryptoList(100)
        cryptoDAO.insert(dbData)
        // Given
        val listFromDB = cryptoDAO.selectAll().first()
        // Then
        assertEquals(dbData, listFromDB)
    }

    @Test
    fun should_insert_and_get_crypto_details() = runTest {
        // Given
        val id = "crypto_id"
        val cryptoDetails = TestDataFactory.randomCryptoDetails(id, id)
        cryptoDAO.insert(cryptoDetails)
        // When
        val cryptoDetailsFromDB = cryptoDAO.selectDetails(id).first()
        // Then
        assertEquals(cryptoDetails, cryptoDetailsFromDB)
    }
}
