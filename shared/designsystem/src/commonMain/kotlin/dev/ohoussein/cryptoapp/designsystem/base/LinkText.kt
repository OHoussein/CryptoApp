package dev.ohoussein.cryptoapp.designsystem.base

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import dev.ohoussein.cryptoapp.designsystem.core.getLinkUrl
import dev.ohoussein.cryptoapp.designsystem.core.htmlToAnnotatedString

@Composable
fun LinkText(
    htmlText: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onLinkClick: (url: String) -> Unit = {},
) {
    val html = remember(htmlText) { htmlText.htmlToAnnotatedString() }
    ClickableText(
        text = html,
        style = style,
        modifier = modifier,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        onClick = { offset ->
            val link = html.getLinkUrl(offset)
            link?.let { onLinkClick(it) }
        }
    )
}
