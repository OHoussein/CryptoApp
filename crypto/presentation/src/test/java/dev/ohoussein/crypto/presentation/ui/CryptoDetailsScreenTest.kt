package dev.ohoussein.crypto.presentation.ui

import app.cash.paparazzi.Paparazzi
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import dev.ohoussein.core.test.coroutine.TestCoroutineRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CryptoDetailsScreenTest(
    @TestParameter val config: PaparazziConfig,
) {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = config.deviceConfig,
        theme = config.theme,
    )

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Test
    fun screen() {
        paparazzi.snapshot {
            PreviewCryptoDetails(darkTheme = config.theme == DARK_THEME)
        }
    }
}
