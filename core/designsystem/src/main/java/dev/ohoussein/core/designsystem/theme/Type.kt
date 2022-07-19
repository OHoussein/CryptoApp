package dev.ohoussein.core.designsystem.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.ohoussein.core.designsystem.R

val PaytoneOneFontFamily = FontFamily(
    Font(R.font.paytone_one_regular),
)

val MontserratFontFamily = FontFamily(
    Font(R.font.montserrat_medium),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
)
