package dev.ohoussein.crypto.presentation.ui

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import dev.ohoussein.core.test.coroutine.TestCoroutineRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CryptoListScreenTest(
    @TestParameter val config: PaparazziConfig,
) {

    @get:Rule
    val paparazzi = getPaparazzi(config)

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun screen() {
        paparazzi.snapshot {
            PreviewCryptoList(darkTheme = config.theme == DARK_THEME)
        }
    }
}
