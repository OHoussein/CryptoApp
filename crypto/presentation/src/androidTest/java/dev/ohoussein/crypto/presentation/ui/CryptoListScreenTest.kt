package dev.ohoussein.crypto.presentation.ui

import android.content.res.Resources
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.ohoussein.core.test.activity.TestActivity
import dev.ohoussein.core.test.mock.TestDataFactory
import dev.ohoussein.crypto.data.di.CryptoDataModule
import dev.ohoussein.crypto.domain.model.DomainCrypto
import dev.ohoussein.crypto.domain.repo.ICryptoRepository
import dev.ohoussein.crypto.presentation.NavPath
import dev.ohoussein.crypto.presentation.testutil.TestNavHost
import dev.ohoussein.crypto.presentation.viewmodel.CryptoListViewModel
import dev.ohoussein.cryptoapp.core.designsystem.R as coreR
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.doSuspendableAnswer
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(value = [CryptoDataModule::class])
@LargeTest
class CryptoListScreenTest {

    @Inject
    internal lateinit var cryptoRepo: ICryptoRepository

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    private val res: Resources
        get() = composeTestRule.activity.resources

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun should_show_list_crypto() {
        givenListOfCrypto { data ->
            // When
            setupContent {
                // Then
                thenCryptoListShouldBeDisplayed(data)
            }
        }
    }

    @Test
    fun should_show_error_screen_and_retry() {
        // Given error
        givenErrorThanSuccessGetListOfCrypto { data ->
            // When
            setupContent {
                // Then
                thenShouldDisplayError()

                composeTestRule.onNodeWithText(res.getString(coreR.string.core_retry))
                    .performClick()

                thenCryptoItemShouldBeDisplayed(data.first())
            }
        }
    }

    @Test
    fun should_show_error_message_and_retry_when_refreshing() {
        givenListOfCrypto { data ->
            // When
            setupContent {
                // check just the first item
                thenCryptoItemShouldBeDisplayed(data.first())
                givenErrorOnRefreshListOfCrypto {
                    swipeToRefreshList()
                    // Then should display error and still see the list
                    thenShouldDisplayError()
                    thenCryptoItemShouldBeDisplayed(data.first())
                }
            }
        }
    }

    // /////////////////////////////////////////////////////////////////////////
    // private methods
    // /////////////////////////////////////////////////////////////////////////

    private fun setupContent(
        next: ComposeContentTestRule.() -> Unit,
    ) {
        composeTestRule.setContent {
            TestNavHost(path = NavPath.HOME) {
                val viewModel = hiltViewModel<CryptoListViewModel>()
                CryptoListScreen(
                    viewModel = viewModel,
                    onClick = {},
                )
            }
        }
        next(composeTestRule)
    }

    private fun swipeToRefreshList() {
        composeTestRule.onNodeWithTag(CryptoListTestTag).performTouchInput {
            swipeDown()
        }
    }

    private fun givenListOfCrypto(next: (List<DomainCrypto>) -> Unit) {
        val data = TestDataFactory.makeCryptoList(20)
        whenever(cryptoRepo.getTopCryptoList())
            .thenReturn(flowOf(data))
        next(data)
    }

    private fun givenErrorOnRefreshListOfCrypto(next: (List<DomainCrypto>) -> Unit) {
        val successData = TestDataFactory.makeCryptoList(20)
        runBlocking {
            whenever(cryptoRepo.refreshTopCryptoList())
                .then { error("Network error") }
        }
        next(successData)
    }

    private fun givenErrorThanSuccessGetListOfCrypto(next: (List<DomainCrypto>) -> Unit) {
        val flow = MutableSharedFlow<List<DomainCrypto>>()
        val successData = TestDataFactory.makeCryptoList(20)
        whenever(cryptoRepo.getTopCryptoList()).thenReturn(flow)
        runBlocking {
            whenever(cryptoRepo.refreshTopCryptoList())
                .then { error("Network error") }
                .doSuspendableAnswer { flow.emit(successData) }
        }
        next(successData)
    }

    private fun thenCryptoListShouldBeDisplayed(data: List<DomainCrypto>) {
        data.forEachIndexed { index, item ->
            thenCryptoItemShouldBeDisplayed(item)
            composeTestRule.onNodeWithTag(CryptoListTestTag)
                .performScrollToIndex(index)
        }
    }

    private fun thenCryptoItemShouldBeDisplayed(item: DomainCrypto) {
        composeTestRule.onNodeWithText(
            item.name,
            useUnmergedTree = true
        ).assertExists()
    }

    private fun thenShouldDisplayError() {
        composeTestRule.onNodeWithText(res.getString(coreR.string.core_retry)).assertIsDisplayed()
    }
}
