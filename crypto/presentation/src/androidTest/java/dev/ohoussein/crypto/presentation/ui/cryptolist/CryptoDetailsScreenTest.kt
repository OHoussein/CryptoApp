package dev.ohoussein.crypto.presentation.ui.cryptolist

import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.filters.LargeTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.ohoussein.core.test.activity.TestActivity
import dev.ohoussein.core.test.mock.TestDataFactory
import dev.ohoussein.crypto.data.di.CryptoDataModule
import dev.ohoussein.crypto.domain.model.DomainCryptoDetails
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.presentation.components.CryptoDetailsScreen
import dev.ohoussein.crypto.presentation.navigation.NavPath
import dev.ohoussein.crypto.presentation.ui.testutil.TestNavHost
import dev.ohoussein.crypto.presentation.viewmodel.CryptoDetailsViewModel
import dev.ohoussein.cryptoapp.core.formatter.ErrorMessageFormatter
import dev.ohoussein.cryptoapp.common.navigation.ExternalRouter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import dev.ohoussein.cryptoapp.core.designsystem.R as coreR

@HiltAndroidTest
@UninstallModules(value = [CryptoDataModule::class])
@LargeTest
class CryptoDetailsScreenTest {

    @Inject
    internal lateinit var cryptoRepo: ICryptoRepository

    @Inject
    lateinit var errorMessageMapper: ErrorMessageFormatter

    @Inject
    lateinit var externalRouter: ExternalRouter

    @get:Rule(order = 1)
    internal val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    internal val composeTestRule = createAndroidComposeRule<TestActivity>()

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
                    composeTestRule.onNodeWithText(res.getString(coreR.string.core_retry))
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
            next: ComposeContentTestRule.() -> Unit,
    ) {
        composeTestRule.setContent {
            TestNavHost(NavPath.CryptoDetailsPath.PATH) {
                val viewModel = hiltViewModel<CryptoDetailsViewModel>()
                CryptoDetailsScreen(
                        viewModel = viewModel,
                        cryptoId = cryptoId,
                        errorMessageFormatter = errorMessageMapper,
                        externalRouter = externalRouter,
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
        composeTestRule.onNodeWithText(res.getString(coreR.string.core_retry)).assertIsDisplayed()
    }
}