package dev.ohoussein.crypto.presentation.ui

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.androidHome
import app.cash.paparazzi.detectEnvironment
import com.android.resources.NightMode

internal const val DARK_THEME = "android:Theme.Material.Dark.NoActionBar"
internal const val LIGHT_THEME = "android:Theme.Material.Light.NoActionBar"

@Suppress("unused")
enum class PaparazziConfig(val deviceConfig: DeviceConfig, val theme: String) {
    DARK(
        deviceConfig = DeviceConfig.PIXEL_5.copy(nightMode = NightMode.NIGHT),
        theme = DARK_THEME,
    ),
    LIGHT(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = LIGHT_THEME,
    ),
    BIG_FONT__LIGHT(
        deviceConfig = DeviceConfig.PIXEL_5.copy(fontScale = 1.5F),
        theme = LIGHT_THEME,
    ),
    TABLET(
        deviceConfig = DeviceConfig.NEXUS_10,
        theme = LIGHT_THEME,
    ),
}

fun getPaparazzi(config: PaparazziConfig) = Paparazzi(
    deviceConfig = config.deviceConfig,
    theme = config.theme,
    // Workaround for sdk 33
    environment = detectEnvironment().copy(
        platformDir = "${androidHome()}/platforms/android-31",
        compileSdkVersion = 31
    )
)
