package dev.ohoussein.cryptoapp.injection

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import dev.ohoussein.core.injection.androidAppModules
import dev.ohoussein.cryptoapp.di.appExtensionModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito.mock

class CheckModulesTest : KoinTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mock(clazz.java)
    }

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun test_di_modules() {
        koinApplication {
            modules(appExtensionModule)
            modules(androidAppModules)
            checkModules {
                withInstance<Context>()
                withInstance<Application>()
                withInstance<SavedStateHandle>()
            }
        }
    }
}
