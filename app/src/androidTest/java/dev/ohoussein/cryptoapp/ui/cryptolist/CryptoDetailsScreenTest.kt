package dev.ohoussein.cryptoapp.ui.cryptolist

import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.ohoussein.cryptoapp.R
import dev.ohoussein.cryptoapp.di.CoreModule
import dev.ohoussein.cryptoapp.di.DataRepoModule
import dev.ohoussein.cryptoapp.domain.model.DomainCryptoDetails
import dev.ohoussein.cryptoapp.domain.repo.ICryptoRepository
import dev.ohoussein.cryptoapp.mock.TestDataFactory
import dev.ohoussein.cryptoapp.ui.activity.RootActivity
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.components.CryptoDetailsScreen
import dev.ohoussein.cryptoapp.ui.feature.cryptolist.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.ui.navigation.NavPath
import dev.ohoussein.cryptoapp.ui.testutil.TestNavHost
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(value = [CoreModule::class, DataRepoModule::class])
class CryptoDetailsScreenTest {

    @Inject
    internal lateinit var cryptoRepo: ICryptoRepository

    @get:Rule(order = 1)
    internal val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    internal val composeTestRule = createAndroidComposeRule<RootActivity>()

    private val res: Resources
        get() = composeTestRule.activity.resources

    private val cryptoId = "bitcoin"

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun should_show_details() {
        givenCrypto { data ->
            //When
            setupContent {
                //Then
                thenCryptoDetailsShouldBeDisplayed(data)
            }
        }
    }

    @Test
    fun should_show_error_screen_and_retry() {
        //Given error
        givenError {
            //When
            setupContent {
                //Then
                thenShouldDisplayError()
                givenCrypto { data ->
                    composeTestRule.onNodeWithText(res.getString(R.string.retry))
                        .performClick()
                    thenCryptoDetailsShouldBeDisplayed(data)
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // private methods
    ///////////////////////////////////////////////////////////////////////////

    private fun setupContent(
        next: ComposeContentTestRule.() -> Unit
    ) {
        composeTestRule.setContent {
            TestNavHost(NavPath.CryptoDetailsPath.PATH) {
                val viewModel = hiltViewModel<CryptoDetailsViewModel>()
                CryptoDetailsScreen(
                    viewModel = viewModel,
                    cryptoId = cryptoId,
                    errorMessageMapper = composeTestRule.activity.errorMessageMapper,
                    externalNavigator = composeTestRule.activity.externalNavigator,
                    onBackClicked = {}
                )
            }
        }
        next(composeTestRule)
    }

    private fun givenCrypto(next: (DomainCryptoDetails) -> Unit) {
        val data = TestDataFactory.randomCryptoDetails(cryptoId)
        whenever(cryptoRepo.getCryptoDetails(cryptoId)).thenReturn(flowOf(data))
        next(data)
    }

    private fun givenError(next: () -> Unit) {
        val dataFlow = flow<DomainCryptoDetails> { throw IOException() }
        whenever(cryptoRepo.getCryptoDetails(any())).thenReturn(dataFlow)
        next()
    }

    private fun thenCryptoDetailsShouldBeDisplayed(item: DomainCryptoDetails) {
        with(composeTestRule) {
            onNode(hasText(item.name, ignoreCase = true, substring = true)).assertIsDisplayed()
            onNode(hasText(item.description, ignoreCase = true)).assertIsDisplayed()
        }

    }

    private fun thenShouldDisplayError() {
        composeTestRule.onNodeWithText(res.getString(R.string.retry)).assertIsDisplayed()
    }
}