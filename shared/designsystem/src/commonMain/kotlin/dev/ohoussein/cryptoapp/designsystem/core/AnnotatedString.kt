package dev.ohoussein.cryptoapp.designsystem.core

import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

internal const val TAG_URL = "URL"

private data class HtmlTag(
    val name: String,
    val params: Map<String, Any>,
    val content: String,
    val startPosition: Int,
    val endPosition: Int,
)

@Suppress("MagicNumber")
private fun String.getAllTags(): List<HtmlTag> {
    val regex = Regex("<([a-zA-Z0-9]+)([^>]*)>(.*?)</\\1>")
    val tags = mutableListOf<HtmlTag>()

    regex.findAll(this).forEach { matchResult ->
        val tagName = matchResult.groups[1]?.value ?: return@forEach
        val rawAttributes = matchResult.groups[2]?.value?.trim().orEmpty()
        val content = matchResult.groups[3]?.value.orEmpty()
        val startPosition = matchResult.range.first
        val endPosition = matchResult.range.last + 1

        val attributes = rawAttributes
            .split(Regex("\\s+"))
            .filter { it.contains("=") }
            .associate {
                val parts = it.split("=", limit = 2)
                val key = parts[0]
                val value = parts.getOrNull(1)?.removeSurrounding("\"", "\"") ?: ""
                key to value
            }

        tags.add(HtmlTag(tagName, attributes, content, startPosition, endPosition))
    }
    return tags
}

fun String.htmlToAnnotatedString(): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        var currentIndex = 0
        getAllTags().forEach { tag ->
            append(this@htmlToAnnotatedString.substring(currentIndex, tag.startPosition))
            when (tag.name) {
                "a" -> withLink(LinkAnnotation.Url(tag.params["href"]?.toString().orEmpty())) {
                    withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append(tag.content)
                    }
                }
                "b" -> withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(tag.content)
                }
                "p" -> {
                    append("\n")
                    append(tag.content)
                    append("\n")
                }
            }
            currentIndex = tag.endPosition
        }
        append(this@htmlToAnnotatedString.substring(currentIndex))
    }
    return annotatedString
}

fun AnnotatedString.getLinkUrl(offset: Int): String? =
    getStringAnnotations(
        tag = TAG_URL,
        start = offset,
        end = offset,
    ).firstOrNull { offset >= it.start && offset <= it.end }?.item
