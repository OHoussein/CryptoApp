package dev.ohoussein.cryptoapp.presentation.ext

import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT

fun String.asAnnotatedString() = buildAnnotatedString {
    val html = HtmlCompat.fromHtml(this@asAnnotatedString, FROM_HTML_MODE_COMPACT)
    append(html.toString())
}
