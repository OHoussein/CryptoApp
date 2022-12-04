package dev.ohoussein.cryptoapp.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BlueGrey700,
    primaryVariant = BlueGrey500,
    secondary = LightBlue300,
    surface = BlueGrey900,
    onPrimary = Color.White,
)

private val LightColorPalette = lightColors(
    primary = BlueGrey700,
    primaryVariant = BlueGrey500,
    secondary = LightBlue500,
    onPrimary = Color.White,
    surface = BlueGrey50,
        /* Other default colors to override
        background = Color.White,
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.Black,
        onBackground = Color.Black,
        onSurface = Color.Black,
        */
)

val AppbarFontFamily = PaytoneOneFontFamily

val PositiveColor = Green500
val NegativeColor = Red500

@Composable
fun CryptoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: Colors? = null,
    content: @Composable () -> Unit,
) {
    val themeColors = colors ?: if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = themeColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
