package dev.ohoussein.cryptoapp.data.database

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import dev.ohoussein.core.test.coroutine.TestCoroutineRule
import dev.ohoussein.crypto.data.database.CryptoDAO
import dev.ohoussein.cryptoapp.data.testutils.TestDataFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class CryptoDAOTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: CryptoDAO

    @Before
    fun setup() {
        val database = CryptoDatabase.buildForTesting(ApplicationProvider.getApplicationContext())
        dao = database.cryptoDAO()
    }

    @Test
    fun should_insert_and_get_crypto_list() = runBlockingTest {
        // Given
        val dbData = TestDataFactory.makeDBCryptoList(100)
        dao.insert(dbData)
        // Given
        val listFromDB = dao.getAll().first()
        // Then
        assertEquals(dbData.size, listFromDB.size)
        assertEquals(dbData, listFromDB)
    }
}
