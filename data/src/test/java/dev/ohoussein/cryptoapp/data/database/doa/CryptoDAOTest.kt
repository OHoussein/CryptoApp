package dev.ohoussein.cryptoapp.data.database.doa

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import dev.ohoussein.cryptoapp.commonTest.mock.TestDataFactory
import dev.ohoussein.cryptoapp.commonTest.rule.TestCoroutineRule
import dev.ohoussein.cryptoapp.data.database.CryptoDatabase
import dev.ohoussein.cryptoapp.data.database.dao.CryptoDAO
import dev.ohoussein.cryptoapp.data.mapper.DomainModelMapper
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
import java.util.Locale

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class CryptoDAOTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val domainMapper = DomainModelMapper(Locale.US)
    private lateinit var dao: CryptoDAO

    @Before
    fun setup() {
        val database = CryptoDatabase.buildForTesting(ApplicationProvider.getApplicationContext())
        dao = database.cryptoDAO()
    }

    @Test
    fun should_insert_and_get_crypto_list() = runBlockingTest {
        //Given
        val domainData = TestDataFactory.makeCryptoList(100)
        val dbData = domainMapper.toDB(domainData)
        dao.insert(dbData)
        //Given
        val listFromDB = dao.getAll().first()
        //Then
        assertEquals(dbData.size, listFromDB.size)
        assertEquals(dbData, listFromDB)
    }
}