package dev.ohoussein.cryptoapp.designsystem.core

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

internal const val TAG_URL = "URL"

fun String.htmlToAnnotatedString(): AnnotatedString {
    val html = this
    return buildAnnotatedString {
        var cursor = 0

        val tagPattern = Regex("""<(/?)(\w+)[^>]*?>""")
        val tags = tagPattern.findAll(html).toList()

        tags.forEach { matchResult ->
            val tag = matchResult.value
            val start = matchResult.range.first
            val end = matchResult.range.last + 1

            if (start > cursor) {
                append(html.substring(cursor, start))
            }

            val tagName = matchResult.groupValues[2].lowercase()
            val isClosingTag = matchResult.groupValues[1] == "/"

            when (tagName) {
                "b" -> {
                    if (isClosingTag) {
                        pop()
                    } else {
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    }
                }

                "p" -> {
                    if (isClosingTag) {
                        append("\n\n")
                    }
                }

                "a" -> {
                    if (isClosingTag) {
                        pop() // Pop the SpanStyle
                        pop() // Pop the StringAnnotation
                    } else {
                        val hrefPattern = Regex("""href="([^"]+)"""")
                        val href = hrefPattern.find(tag)?.groupValues?.get(1) ?: ""
                        pushStringAnnotation(tag = TAG_URL, annotation = href)
                        pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                    }
                }
            }

            cursor = end
        }

        if (cursor < html.length) {
            append(html.substring(cursor))
        }
    }
}

fun AnnotatedString.getLinkUrl(offset: Int): String? =
    getStringAnnotations(
        tag = TAG_URL,
        start = offset,
        end = offset,
    ).firstOrNull { offset >= it.start && offset <= it.end }?.item
