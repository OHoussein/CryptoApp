package dev.ohoussein.cryptoapp.designsystem.core

import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import kotlin.test.Test
import kotlin.test.assertEquals

class AnnotatedStringTest {

    @Test
    fun `Given a html with bold tag When htmlToAnnotatedString it should return the right annotatedString`() {
        val pureText = "This is bold text."
        val html = "This is <b>bold</b> text."

        val annotatedString = html.htmlToAnnotatedString()

        val linkAnnotations = annotatedString.getLinkAnnotations(0, html.length)
        assertEquals(pureText, annotatedString.text)
        assertEquals(0, linkAnnotations.size)
        assertEquals(1, annotatedString.spanStyles.size)
        with(annotatedString.spanStyles.first()) {
            assertEquals(FontWeight.Bold, item.fontWeight)
            assertEquals(pureText.indexOf("bold"), start)
            assertEquals(pureText.indexOf(" text."), end)
        }
    }

    @Test
    fun `Given a html with paragraph tags When htmlToAnnotatedString it should return the right annotatedString`() {
        val html = "<p>First paragraph.</p><p>Second paragraph.</p>"
        val annotatedString = html.htmlToAnnotatedString()
        val expected = buildAnnotatedString {
            append("\nFirst paragraph.\n")
            append("\nSecond paragraph.\n")
        }
        assertEquals(expected, annotatedString)
    }

    @Test
    fun `Given a html with link tag When htmlToAnnotatedString it should return the right annotatedString`() {
        val pureText = "This is an example link."
        val html = """This is an <a href="https://example.com">example link</a>."""

        val annotatedString = html.htmlToAnnotatedString()

        val linkAnnotation = annotatedString.getLinkAnnotations(
            start = html.indexOf("<a"),
            end = html.lastIndexOf("</a>"),
        )
        assertEquals(1, linkAnnotation.size)
        assertEquals(0, annotatedString.getStringAnnotations(0, html.length).size)
        with(linkAnnotation.first()) {
            assertEquals(LinkAnnotation.Url("https://example.com"), item)
            assertEquals(pureText.indexOf("example link"), start)
            assertEquals(pureText.lastIndexOf("."), end)
        }
        assertEquals(pureText, annotatedString.text)
    }

    @Test
    fun `Given a html with 2 links tag When getLinkUrl it should return the correct URL`() {
        val pureText = "This is the first link and this is the second one."
        val html =
            """This is the <a href="https://first.com">first link</a> and this is the <a href="https://second.com">second one</a>."""

        val annotatedString = html.htmlToAnnotatedString()

        val linkAnnotations = annotatedString.getLinkAnnotations(0, html.length)
        val stringAnnotations = annotatedString.getStringAnnotations(0, html.length)
        assertEquals(pureText, annotatedString.text)
        assertEquals(2, linkAnnotations.size)
        assertEquals(0, stringAnnotations.size)
        with(linkAnnotations[0]) {
            assertEquals(LinkAnnotation.Url("https://first.com"), item)
            assertEquals(pureText.indexOf("first link"), start)
            assertEquals(pureText.lastIndexOf(" and this is"), end)
        }
        with(linkAnnotations[1]) {
            assertEquals(LinkAnnotation.Url("https://second.com"), item)
            assertEquals(pureText.indexOf("second one"), start)
            assertEquals(pureText.lastIndexOf("."), end)
        }
    }

    @Test
    fun `Given a plain text When getLinkUrl it should return null`() {
        val html = "This is plain text."
        val annotatedString = html.htmlToAnnotatedString()

        val linkAnnotations = annotatedString.getLinkAnnotations(0, html.length)
        val stringAnnotations = annotatedString.getStringAnnotations(0, html.length)
        assertEquals("This is plain text.", annotatedString.text)
        assertEquals(0, linkAnnotations.size)
        assertEquals(0, stringAnnotations.size)
    }
}
