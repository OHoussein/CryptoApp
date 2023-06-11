package dev.ohoussein.cryptoapp.core.sharedmodules

import android.app.Application
import android.content.Context
import io.mockk.mockkClass
import kotlin.test.Test
import org.junit.Rule
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProviderRule

class CheckSharedModulesTest {

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        mockkClass(clazz)
    }

    @Test
    fun test_sharedModules() {
        koinApplication {
            modules(sharedModules)
            checkModules {
                withInstance<Context>()
                withInstance<Application>()
            }
        }
    }
}
