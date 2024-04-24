@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class, ExperimentalResourceApi::class)

package dev.ohoussein.cryptoapp.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cryptoapp.shared.designsystem.generated.resources.Res
import cryptoapp.shared.designsystem.generated.resources.montserrat_medium
import cryptoapp.shared.designsystem.generated.resources.paytone_one_regular
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

val PaytoneOneFontFamily
    @Composable get() = FontFamily(
        Font(Res.font.paytone_one_regular),
    )

val MontserratFontFamily
    @Composable get() = FontFamily(
        Font(Res.font.montserrat_medium),
    )

val Typography
    @Composable get() = Typography(
        body1 = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 24.sp,
        ),
        body2 = TextStyle(
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 24.sp,
        ),
        caption = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp
        ),
    )
