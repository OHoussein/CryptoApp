package dev.ohoussein.crypto.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ohoussein.crypto.presentation.UIConfig.CRYPTO_DESCRIPTION_MAX_LINES
import dev.ohoussein.crypto.presentation.debug.DataPreview.previewCryptoDetails
import dev.ohoussein.crypto.presentation.model.CryptoDetails
import dev.ohoussein.crypto.presentation.model.LabelValue
import dev.ohoussein.crypto.ui.R
import dev.ohoussein.cryptoapp.presentation.theme.NegativeColor
import dev.ohoussein.cryptoapp.presentation.theme.PositiveColor
import dev.ohoussein.cryptoapp.presentation.util.asAnnotatedString

@Composable
fun CryptoDetailsHeader(
    modifier: Modifier = Modifier,
    crypto: CryptoDetails,
) {
    var descriptionOverflow by remember { mutableStateOf(false) }
    var expandDescription by remember { mutableStateOf(false) }

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CryptoImage(
                modifier = Modifier.size(48.dp),
                imageUrl = crypto.base.imageUrl,
            )
            Text(
                text = "${crypto.base.name} (${crypto.base.symbol})",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        crypto.sentimentUpVotesPercentage?.let {
            CryptoSentiment(
                modifier = Modifier.padding(8.dp),
                upPercentSentiment = it,
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = crypto.description.asAnnotatedString(),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(top = 8.dp),
                overflow = TextOverflow.Clip,
                maxLines = if (expandDescription) Int.MAX_VALUE else CRYPTO_DESCRIPTION_MAX_LINES,
                onTextLayout = { layout ->
                    descriptionOverflow = !layout.hasVisualOverflow
                }
            )
            if (!descriptionOverflow)
                TextButton(onClick = { expandDescription = true }) {
                    Icon(
                        imageVector = Icons.Outlined.ExpandMore,
                        contentDescription = null,
                        modifier = Modifier
                            .size(18.dp)
                            .padding(end = 2.dp),
                        tint = MaterialTheme.colors.onSurface,
                    )
                    Text(
                        text = stringResource(id = R.string.crypto_see_more),
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.caption,
                    )
                }
        }
    }
}

@Composable
fun CryptoSentiment(
    modifier: Modifier = Modifier,
    upPercentSentiment: LabelValue<Double>,
) {
    Column(modifier.width(IntrinsicSize.Min)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_bull),
                contentDescription = "",
                tint = PositiveColor,
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_bear),
                contentDescription = "",
                tint = NegativeColor,
            )
        }

        @Suppress("MagicNumber")
        LinearProgressIndicator(
            progress = upPercentSentiment.value.toFloat() / 100F,
            color = PositiveColor,
            backgroundColor = NegativeColor,
        )
    }
}

@Composable
fun CryptoLinks(
    modifier: Modifier = Modifier,
    crypto: CryptoDetails,
    onHomePageClicked: (CryptoDetails) -> Unit,
    onBlockchainSiteClicked: (CryptoDetails) -> Unit,
    onSourceCodeClicked: (CryptoDetails) -> Unit,
) {

    Column(modifier = modifier.width(IntrinsicSize.Min)) {
        Text(
            text = stringResource(id = R.string.crypto_details_links_section),
            style = MaterialTheme.typography.h6,
        )
        if (crypto.homePageUrl != null) {
            ItemLink(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onHomePageClicked(crypto) },
                icon = Icons.Outlined.Home,
                label = stringResource(id = R.string.crypto_details_link_home),
            )
        }
        if (crypto.blockchainSite != null) {
            ItemLink(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBlockchainSiteClicked(crypto) },
                icon = Icons.Outlined.Link,
                label = stringResource(id = R.string.crypto_details_link_blockchain),
            )
        }
        if (crypto.mainRepoUrl != null) {
            ItemLink(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSourceCodeClicked(crypto) },
                icon = Icons.Outlined.Code,
                label = stringResource(id = R.string.crypto_details_link_source_code),
            )
        }
    }
}

@Composable
fun ItemLink(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
) {
    Row(
        modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .size(28.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.body2,
            )
        }
        Icon(
            imageVector = Icons.Outlined.NavigateNext,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
        )
    }
}

@Preview
@Composable
private fun PreviewCryptoDetails() {
    CryptoDetailsHeader(
        crypto = previewCryptoDetails,
    )
}

@Preview
@Composable
private fun PreviewCryptoLinks() {
    CryptoLinks(
        modifier = Modifier.fillMaxWidth(),
        crypto = previewCryptoDetails,
        onHomePageClicked = {},
        onBlockchainSiteClicked = {},
        onSourceCodeClicked = {},
    )
}
