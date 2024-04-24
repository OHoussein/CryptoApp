package dev.ohoussein.cryptoapp.crypto.presentation.uicomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.ohoussein.cryptoapp.crypto.presentation.model.Crypto
import dev.ohoussein.cryptoapp.designsystem.theme.NegativeColor
import dev.ohoussein.cryptoapp.designsystem.theme.PositiveColor

@Composable
fun CryptoItem(
    modifier: Modifier = Modifier,
    crypto: Crypto,
    onClick: (Crypto) -> Unit,
) {
    Box(modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
        Card(elevation = 12.dp) {
            // this additional box is a workaround for the ripple on the card background corner
            Box(
                modifier = Modifier.clickable { onClick(crypto) }
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CryptoImage(
                        modifier = Modifier.size(48.dp),
                        imageUrl = crypto.info.imageUrl,
                    )
                    Column(Modifier.padding(horizontal = 8.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = crypto.info.name,
                                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colors.onSurface,
                            )
                            Text(
                                text = crypto.price.labelValue.label,
                                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colors.onSurface,
                            )
                        }
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = crypto.info.symbol,
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                            crypto.priceChangePercentIn24h?.let { priceChangePercentIn24h ->
                                Text(
                                    text = priceChangePercentIn24h.label,
                                    style = MaterialTheme.typography.subtitle1,
                                    color = if (priceChangePercentIn24h.value >= 0) PositiveColor else NegativeColor,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CryptoImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
    )
}

/*
@Preview
@Composable
private fun PreviewCryptoItem() {
    CryptoItem(
        crypto = previewCrypto,
        onClick = {},
    )
}
*/