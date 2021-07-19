package dev.ohoussein.cryptoapp.ui.core.util

import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT

fun String.asAnnotatedString() = buildAnnotatedString {
    val html = HtmlCompat.fromHtml(this@asAnnotatedString, FROM_HTML_MODE_COMPACT)
    append(html.toString())
/*    html.getSpans<CharacterStyle>()
        //.filter { it !is URLSpan }
        .forEach { span ->
            val start = html.getSpanStart(span)
            val end = html.getSpanEnd(span)
            val content = html.slice(start until end).toString()
            append(content)
            when (span) {
                     is StyleSpan -> {
                         when (span.style) {
                             Typeface.BOLD -> {
                                 withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                     append(content)
                                 }
                             }
                         }
                     }
                     //handle only those tags so far
                     else -> {
                         append(content)
                     }
                 }
        }*/

}