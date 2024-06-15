package dev.ohoussein.cryptoapp.designsystem.core

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AnnotatedStringTest {

    @Test
    fun `Given a html with bold tag When htmlToAnnotatedString it should return the right annotatedString`() {
        val html = "This is <b>bold</b> text."
        val annotatedString = html.htmlToAnnotatedString()
        val expected = buildAnnotatedString {
            append("This is ")
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append("bold")
            pop()
            append(" text.")
        }
        assertEquals(expected, annotatedString)
    }

    @Test
    fun `Given a html with paragraph tags When htmlToAnnotatedString it should return the right annotatedString`() {
        val html = "<p>First paragraph.</p><p>Second paragraph.</p>"
        val annotatedString = html.htmlToAnnotatedString()
        val expected = buildAnnotatedString {
            append("First paragraph.\n\n")
            append("Second paragraph.\n\n")
        }
        assertEquals(expected, annotatedString)
    }

    @Test
    fun `Given a html with link tag When htmlToAnnotatedString it should return the right annotatedString`() {
        val html = """This is an <a href="https://example.com">example link</a>."""
        val annotatedString = html.htmlToAnnotatedString()
        val expected = buildAnnotatedString {
            append("This is an ")
            pushStringAnnotation(tag = TAG_URL, annotation = "https://example.com")
            pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
            append("example link")
            pop()
            pop()
            append(".")
        }
        assertEquals(expected.spanStyles, annotatedString.spanStyles)
        assertEquals(expected, annotatedString)
    }

    @Test
    fun `Given a html with link tag When getLinkUrl it should return the correct URL`() {
        val html = """This is an <a href="https://example.com">example link</a>."""
        val annotatedString = html.htmlToAnnotatedString()
        val url = annotatedString.getLinkUrl(13)
        assertEquals("https://example.com", url)
    }

    @Test
    fun `Given a html with 2 links tag When getLinkUrl it should return the correct URL`() {
        val html = """This is the <a href="https://first.com">first link</a> and this is the <a href="https://second.com">second one</a>."""
        val annotatedString = html.htmlToAnnotatedString()
        val url = annotatedString.getLinkUrl(41)
        assertEquals("https://second.com", url)
    }

    @Test
    fun `Given a plain text When getLinkUrl it should return null`() {
        val html = "This is plain text."
        val annotatedString = html.htmlToAnnotatedString()
        val offset = html.indexOf("plain")
        val url = annotatedString.getLinkUrl(offset)
        assertNull(url)
    }
}
